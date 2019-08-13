package com.woowacourse.zzazanstagram.model.article.controller;

import com.woowacourse.zzazanstagram.model.article.ArticleAssembler;
import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleRequest;
import com.woowacourse.zzazanstagram.model.article.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ArticleController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private static final String TAG = "[ArticleController]";

    private ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/articles/new")
    public String createArticle() {
        return "article-edit";
    }

    @PostMapping("/articles")
    public String create(@Valid ArticleRequest dto) {
        Article article = ArticleAssembler.toEntity(dto);
        articleRepository.save(article);

        log.info("{} create() >> {}", TAG, article);

        return "redirect:/";
    }
}
