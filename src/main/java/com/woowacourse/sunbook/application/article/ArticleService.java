package com.woowacourse.sunbook.application.article;

import com.woowacourse.sunbook.application.article.dto.ArticleResponseDto;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.ArticleRepository;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.presentation.excpetion.NotFoundArticleException;
import com.woowacourse.sunbook.seongmo.NotMatchUserException;
import com.woowacourse.sunbook.seongmo.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleService(final ArticleRepository articleRepository, final UserService userService, final ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ArticleResponseDto save(ArticleFeature articleFeature, Long userId) {
        User author = userService.findUserById(userId);
        Article savedArticle = articleRepository.save(new Article(articleFeature, author));

        return modelMapper.map(savedArticle, ArticleResponseDto.class);
    }

    @Transactional
    public ArticleResponseDto modify(long articleId, ArticleFeature articleFeature, Long userId) {
        Article article = articleRepository.findById(articleId).orElseThrow(NotFoundArticleException::new);
        User user = userService.findUserById(userId);
        if (article.isSameUser(user)) {
            article.update(articleFeature);

            return modelMapper.map(article, ArticleResponseDto.class);
        }
        throw new NotMatchUserException(userId);
    }

    public List<ArticleResponseDto> findPageByAuthor(Pageable pageable, Long userId) {
        User foundUser = userService.findUserById(userId);
        return Collections.unmodifiableList(
                articleRepository.findAllByAuthor(pageable, foundUser).getContent().stream()
                        .map(article -> modelMapper.map(article, ArticleResponseDto.class))
                        .collect(Collectors.toList())
        );
    }

    public void remove(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId).orElseThrow(NotFoundArticleException::new);
        User user = userService.findUserById(userId);
        if (article.isSameUser(user)) {
            articleRepository.deleteById(articleId);

            return;
        }
        throw new NotMatchUserException(userId);
    }
}
