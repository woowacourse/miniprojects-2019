package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleApiService;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    ApplicationContext applicationContext;

    private ArticleApiService articleApiService;

    public HomeController(ArticleApiService articleApiService) {
        this.articleApiService = articleApiService;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        List<Article> articles = articleApiService.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }
}
