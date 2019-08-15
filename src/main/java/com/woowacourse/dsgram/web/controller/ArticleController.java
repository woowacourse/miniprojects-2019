package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleApiService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleApiService articleApiService;

    @GetMapping("/writing")
    public String moveToWritePage() {
        return "article-edit";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model) {
        Article article = articleApiService.findById(articleId);
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/{articleId}/file")
    public ResponseEntity<byte[]> showArticleFile(@PathVariable long articleId) throws IOException {
        Article article = articleApiService.findById(articleId);
        File file = new File(article.getFilePath() +"/" + article.getFileName());
        byte[] bytes = Files.readAllBytes(file.toPath());
        byte[] base64 = Base64.getEncoder().encode(bytes);

        return new ResponseEntity<>(base64, HttpStatus.OK);
    }

}
