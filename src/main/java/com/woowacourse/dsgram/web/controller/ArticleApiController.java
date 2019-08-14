package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleApiService;
import com.woowacourse.dsgram.service.FileService;
import com.woowacourse.dsgram.service.dto.ArticleRequest;
import com.woowacourse.dsgram.service.vo.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleApiController {

    @Autowired
    ArticleApiService articleApiService;

    @Autowired
    FileService fileService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<HttpStatus> create(ArticleRequest articleRequest) {
        FileInfo fileInfo = fileService.save(articleRequest.getFile());
        Article article = convertFrom(articleRequest, fileInfo);
        articleApiService.create(article);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Article convertFrom(ArticleRequest articleRequest, FileInfo fileInfo) {
        return new Article(articleRequest.getContents(), fileInfo.getFileName(), fileInfo.getFilePath());
    }


}
