package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.article.ArticleRequestDto;
import com.woowacourse.sunbook.application.dto.article.ArticleResponseDto;
import com.woowacourse.sunbook.application.exception.NotFoundArticleException;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.ArticleRepository;
import com.woowacourse.sunbook.domain.article.OpenRange;
import com.woowacourse.sunbook.domain.comment.exception.MismatchAuthException;
import com.woowacourse.sunbook.domain.relation.Relation;
import com.woowacourse.sunbook.domain.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final RelationService relationService;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleService(final ArticleRepository articleRepository,
                          final UserService userService,
                          final RelationService relationService,
                          final ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.relationService = relationService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> findAll(final Long loginId,
                                            final Long pageUserId,
                                            final String target) {
        return Collections.unmodifiableList(
                checkPage(loginId, pageUserId, target).stream()
                        .sorted()
                        .map(article -> modelMapper.map(article, ArticleResponseDto.class))
                        .collect(Collectors.toList())
        );
    }

    private List<Article> checkPage(final Long loginId, final Long pageUserId, final String target) {
        if ("users".equals(target)) {
            return findAllUserPage(loginId, pageUserId);
        }

        return findAllNewsFeed(loginId);
    }

    private List<Article> findAllNewsFeed(final Long loginId) {
        User author = userService.findById(loginId);
        List<User> friends = relationService.getFriendsRelation(author).stream()
                .map(Relation::getTo)
                .collect(Collectors.toList());

        List<Article> newsFeedArticles = new ArrayList<>(articleRepository.findAllByAuthor(author));
            newsFeedArticles.addAll(articleRepository.findAllByOpenRangeAndAuthorNot(OpenRange.ALL, author));
        newsFeedArticles.addAll(articleRepository.findAllByAuthorInAndOpenRange(friends, OpenRange.ONLY_FRIEND));

        return Collections.unmodifiableList(newsFeedArticles);
    }

    private List<Article> findAllUserPage(final Long loginId, final Long pageUserId) {
        if (loginId.equals(pageUserId)) {
            User author = userService.findById(loginId);

            return Collections.unmodifiableList(articleRepository.findAllByAuthor(author));
        }

        return findAllAnotherPage(loginId, pageUserId);
    }

    private List<Article> findAllAnotherPage(final Long loginId, final Long pageUserId) {
        User pageUser = userService.findById(pageUserId);
        List<Article> userPageArticles =
                new ArrayList<>(articleRepository.findAllByAuthorAndOpenRange(pageUser, OpenRange.ALL));

        if (relationService.isFriend(loginId, pageUserId)) {
            userPageArticles.addAll(articleRepository
                    .findAllByAuthorAndOpenRange(pageUser, OpenRange.ONLY_FRIEND));
        }

        return Collections.unmodifiableList(userPageArticles);
    }

    @Transactional
    public ArticleResponseDto save(final ArticleRequestDto articleRequestDto, final Long userId) {
        User author = userService.findById(userId);
        Article savedArticle = articleRepository
                .save(new Article(articleRequestDto.getArticleFeature(), author, articleRequestDto.getOpenRange()));

        return modelMapper.map(savedArticle, ArticleResponseDto.class);
    }

    @Transactional
    public ArticleResponseDto modify(final Long articleId,
                                     final ArticleFeature articleFeature,
                                     final Long userId) {
        Article article = articleRepository.findById(articleId).orElseThrow(NotFoundArticleException::new);
        User user = userService.findById(userId);

        if (article.isSameUser(user)) {
            article.update(articleFeature);

            return modelMapper.map(article, ArticleResponseDto.class);
        }

        throw new MismatchAuthException();
    }

    @Transactional
    public boolean remove(final Long articleId, final Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        User user = userService.findById(userId);

        if (article.isSameUser(user)) {
            articleRepository.deleteById(articleId);

            return true;
        }

        throw new MismatchAuthException();
    }

    protected Article findById(final Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(NotFoundArticleException::new);
    }
}
