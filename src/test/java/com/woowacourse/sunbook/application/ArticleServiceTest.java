package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.application.dto.article.ArticleResponseDto;
import com.woowacourse.sunbook.application.exception.NotFoundArticleException;
import com.woowacourse.sunbook.application.exception.NotFoundUserException;
import com.woowacourse.sunbook.application.service.ArticleService;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.ArticleRepository;
import com.woowacourse.sunbook.domain.comment.exception.MismatchAuthException;
import com.woowacourse.sunbook.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ArticleServiceTest {
    private static final Long ARTICLE_ID = 1L;
    private static final Long USER_ID = 1L;

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleFeature articleFeature;

    @Mock
    private ArticleResponseDto articleResponseDto;

    @Mock
    private ArticleFeature updatedArticleFeature;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Article article;

    @Mock
    private User user;

    @Mock
    private UserService userService;

    @Test
    void 게시글_정상_생성() {
        given(articleRepository.save(any(Article.class))).willReturn(article);
        given(modelMapper.map(article, ArticleResponseDto.class)).willReturn(articleResponseDto);
        given(userService.findUserById(any(Long.class))).willReturn(user);

        articleService.save(articleFeature, USER_ID);

        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void 게시글_생성시_없는_유저() {
        given(userService.findUserById(any(Long.class))).willThrow(NotFoundUserException.class);

        assertThrows(NotFoundUserException.class, () -> articleService.save(articleFeature, USER_ID));
    }

    @Test
    void 게시글_정상_수정() {
        given(articleRepository.findById(ARTICLE_ID)).willReturn(Optional.of(article));
        given(modelMapper.map(article, ArticleResponseDto.class)).willReturn(articleResponseDto);
        given(userService.findUserById(any(Long.class))).willReturn(user);
        given(article.isSameUser(any(User.class))).willReturn(true);

        articleService.modify(ARTICLE_ID, updatedArticleFeature, USER_ID);

        verify(articleRepository).findById(ARTICLE_ID);
    }

    @Test
    void 게시글_수정시_없는_게시글() {
        given(articleRepository.findById(any(Long.class))).willReturn(Optional.empty());

        assertThrows(NotFoundArticleException.class, () -> articleService.modify(ARTICLE_ID, articleFeature, USER_ID));
    }

    @Test
    void 게시글_권한_없는_수정() {
        given(articleRepository.findById(any(Long.class))).willReturn(Optional.of(article));
        given(article.isSameUser(any(User.class))).willReturn(false);

        assertThrows(MismatchAuthException.class, () -> {
            articleService.modify(ARTICLE_ID, updatedArticleFeature, USER_ID);
        });
    }

    @Test
    void 게시글_정상_삭제() {
        given(articleRepository.findById(ARTICLE_ID)).willReturn(Optional.of(article));
        given(userService.findUserById(any(Long.class))).willReturn(user);
        given(article.isSameUser(any(User.class))).willReturn(true);

        articleService.remove(ARTICLE_ID, USER_ID);

        verify(articleRepository).deleteById(ARTICLE_ID);
    }

    @Test
    void 게시글_권한_없는_삭제() {
        given(articleRepository.findById(ARTICLE_ID)).willReturn(Optional.of(article));
        given(article.isSameUser(any(User.class))).willReturn(false);

        assertThrows(MismatchAuthException.class, () -> {
            articleService.remove(ARTICLE_ID, USER_ID);
        });
    }

    @Test
    void 게시글_삭제시_없는_게시글() {
        given(articleRepository.findById(any(Long.class))).willReturn(Optional.empty());

        assertThrows(NotFoundArticleException.class, () -> articleService.remove(ARTICLE_ID, USER_ID));
    }
}
