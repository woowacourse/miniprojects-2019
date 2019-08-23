package com.wootecobook.turkey.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/{keyword}")
    public String form(@PathVariable String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return "search-result";
    }
}
