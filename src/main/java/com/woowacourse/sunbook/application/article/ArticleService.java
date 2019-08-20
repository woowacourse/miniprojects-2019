package com.woowacourse.sunbook.application.article;

import com.woowacourse.sunbook.application.article.dto.ArticleResponseDto;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.ArticleRepository;
import com.woowacourse.sunbook.presentation.excpetion.NotFoundArticleException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ArticleResponseDto save(ArticleFeature articleFeature) {
        Article savedArticle = articleRepository.save(new Article(articleFeature));

        return modelMapper.map(savedArticle, ArticleResponseDto.class);
    }

    @Transactional
    public ArticleResponseDto modify(long articleId, ArticleFeature articleFeature) {
        Article article = articleRepository.findById(articleId).orElseThrow(NotFoundArticleException::new);
        article.update(articleFeature);

        return modelMapper.map(article, ArticleResponseDto.class);
    }

    public List<ArticleResponseDto> findAll() {
        return Collections.unmodifiableList(
                articleRepository.findAll().stream()
                        .map(article -> modelMapper.map(article, ArticleResponseDto.class))
                        .collect(Collectors.toList())
        );
    }

    public Article findById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
//        return modelMapper.map(article, ArticleResponseDto.class);
        return article;
    }

    public void remove(long articleId) {
        articleRepository.deleteById(articleId);
    }
}
