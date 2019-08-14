package com.woowacourse.dsgram.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {

    @GetMapping("/articles/writing")
    public String moveToWritePage() {
        return "article-edit";
    }

}
