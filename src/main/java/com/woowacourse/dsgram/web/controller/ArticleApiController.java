package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.ArticleApiService;
import com.woowacourse.dsgram.service.FileService;
import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.ArticleRequest;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import com.woowacourse.dsgram.service.vo.FileInfo;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {

    private UserService userService;
    private ArticleApiService articleApiService;
    private FileService fileService;

    public ArticleApiController(UserService userService, ArticleApiService articleApiService, FileService fileService) {
        this.userService = userService;
        this.articleApiService = articleApiService;
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<Article> create(ArticleRequest articleRequest, @UserSession LoginUserRequest loginUserRequest) {
        FileInfo fileInfo = fileService.save(articleRequest.getFile());
        Article article = convertFrom(articleRequest, fileInfo, loginUserRequest);
        Article savedArticle = articleApiService.create(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.OK);
    }

    @GetMapping("{articleId}/file")
    public ResponseEntity<byte[]> showArticleFile(@PathVariable long articleId) {
        Article article = articleApiService.findById(articleId);
        byte[] base64 = fileService.readFile(article);

        return new ResponseEntity<>(base64, HttpStatus.OK);
    }

    @PutMapping("{articleId}")
    public ResponseEntity update(@PathVariable long articleId, @RequestBody ArticleEditRequest articleEditRequest, @UserSession LoginUserRequest loginUserRequest) {
        articleApiService.update(articleId, articleEditRequest, loginUserRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{articleId}")
    public ResponseEntity delete(@PathVariable long articleId, @UserSession LoginUserRequest loginUserRequest) {
        articleApiService.delete(articleId, loginUserRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    private Article convertFrom(ArticleRequest articleRequest, FileInfo fileInfo, LoginUserRequest loginUserRequest) {
        User user = userService.findUserById(loginUserRequest.getId());
        return new Article(articleRequest.getContents(), fileInfo.getFileName(), fileInfo.getFilePath(), user);
    }
}
