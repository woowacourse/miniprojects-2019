package com.wootecobook.turkey.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/{keyword}")
    public ModelAndView form(@PathVariable String keyword) {
        return new ModelAndView("search-result", "keyword", keyword);
    }
}
