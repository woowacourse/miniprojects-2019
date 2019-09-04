package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.MockStorage;
import com.woowacourse.sunbook.application.dto.article.ArticleResponseDto;
import com.woowacourse.sunbook.application.exception.NotFoundArticleException;
import com.woowacourse.sunbook.application.exception.NotFoundUserException;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.OpenRange;
import com.woowacourse.sunbook.domain.comment.exception.MismatchAuthException;
import com.woowacourse.sunbook.domain.relation.Relation;
import com.woowacourse.sunbook.domain.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ArticleServiceTest extends MockStorage {
    private static final Long ARTICLE_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long PAGE_USER_ID = 2L;
    private static final String NEWSFEED_PAGE = "newsfeed";
    private static final String USER_PAGE = "users";

    @InjectMocks
    private ArticleService injectArticleService;

    private List<User> friends = new ArrayList<>();

    @Test
    void 뉴스피드_게시글_조회() {
        given(userService.findById(any(Long.class))).willReturn(user);
        given(relationService.getFriendsRelation(user).stream()
                .map(Relation::getTo)
                .collect(Collectors.toList())).willReturn(friends);
        given(modelMapper.map(article, ArticleResponseDto.class)).willReturn(articleResponseDto);

        injectArticleService.findAll(USER_ID, USER_ID, NEWSFEED_PAGE);

        verify(articleRepository).findAllByAuthor(user);
        verify(articleRepository).findAllByOpenRangeAndAuthorNot(OpenRange.ALL, user);
        verify(articleRepository).findAllByAuthorInAndOpenRange(friends, OpenRange.ONLY_FRIEND);
    }

    @Test
    void 자신의_마이페이지_게시글_조회() {
        given(userService.findById(any(Long.class))).willReturn(user);
        given(modelMapper.map(article, ArticleResponseDto.class)).willReturn(articleResponseDto);

        injectArticleService.findAll(USER_ID, USER_ID, USER_PAGE);

        verify(articleRepository).findAllByAuthor(user);
    }

    @Test
    void 친구가_아닌_타인_마이페이지_게시글_조회() {
        given(userService.findById(any(Long.class))).willReturn(user);
        given(relationService.isFriend(USER_ID, PAGE_USER_ID)).willReturn(false);
        given(modelMapper.map(article, ArticleResponseDto.class)).willReturn(articleResponseDto);

        injectArticleService.findAll(USER_ID, PAGE_USER_ID, USER_PAGE);

        verify(articleRepository).findAllByAuthorAndOpenRange(user, OpenRange.ALL);
    }

    @Test
    void 친구인_타인_마이페이지_게시글_조회() {
        given(userService.findById(any(Long.class))).willReturn(user);
        given(relationService.isFriend(USER_ID, PAGE_USER_ID)).willReturn(true);
        given(modelMapper.map(article, ArticleResponseDto.class)).willReturn(articleResponseDto);

        injectArticleService.findAll(USER_ID, PAGE_USER_ID, USER_PAGE);

        verify(articleRepository).findAllByAuthorAndOpenRange(user, OpenRange.ALL);
        verify(articleRepository).findAllByAuthorAndOpenRange(user, OpenRange.ONLY_FRIEND);
    }

    @Test
    void 게시글_정상_생성() {
        given(articleRepository.save(any(Article.class))).willReturn(article);
        given(modelMapper.map(article, ArticleResponseDto.class)).willReturn(articleResponseDto);
        given(userService.findById(any(Long.class))).willReturn(user);

        injectArticleService.save(articleRequestDto, USER_ID);

        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void 게시글_생성시_없는_유저() {
        given(userService.findById(any(Long.class))).willThrow(NotFoundUserException.class);

        assertThrows(NotFoundUserException.class, () -> injectArticleService.save(articleRequestDto, USER_ID));
    }

    @Test
    void 게시글_정상_수정() {
        given(articleRepository.findById(ARTICLE_ID)).willReturn(Optional.of(article));
        given(modelMapper.map(article, ArticleResponseDto.class)).willReturn(articleResponseDto);
        given(userService.findById(any(Long.class))).willReturn(user);
        given(article.isSameUser(any(User.class))).willReturn(true);

        injectArticleService.modify(ARTICLE_ID, updatedArticleFeature, USER_ID);

        verify(articleRepository).findById(ARTICLE_ID);
    }

    @Test
    void 게시글_수정시_없는_게시글() {
        given(articleRepository.findById(any(Long.class))).willReturn(Optional.empty());

        assertThrows(NotFoundArticleException.class, () -> injectArticleService.modify(ARTICLE_ID, articleFeature, USER_ID));
    }

    @Test
    void 게시글_권한_없는_수정() {
        given(articleRepository.findById(any(Long.class))).willReturn(Optional.of(article));
        given(article.isSameUser(any(User.class))).willReturn(false);

        assertThrows(MismatchAuthException.class, () -> {
            injectArticleService.modify(ARTICLE_ID, updatedArticleFeature, USER_ID);
        });
    }

    @Test
    void 게시글_정상_삭제() {
        given(articleRepository.findById(ARTICLE_ID)).willReturn(Optional.of(article));
        given(userService.findById(any(Long.class))).willReturn(user);
        given(article.isSameUser(any(User.class))).willReturn(true);

        injectArticleService.remove(ARTICLE_ID, USER_ID);

        verify(articleRepository).deleteById(ARTICLE_ID);
    }

    @Test
    void 게시글_권한_없는_삭제() {
        given(articleRepository.findById(ARTICLE_ID)).willReturn(Optional.of(article));
        given(article.isSameUser(any(User.class))).willReturn(false);

        assertThrows(MismatchAuthException.class, () -> {
            injectArticleService.remove(ARTICLE_ID, USER_ID);
        });
    }

    @Test
    void 게시글_삭제시_없는_게시글() {
        given(articleRepository.findById(any(Long.class))).willReturn(Optional.empty());

        assertThrows(NotFoundArticleException.class, () -> injectArticleService.remove(ARTICLE_ID, USER_ID));
    }
}
