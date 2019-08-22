package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.ArticleFileNamingStrategy;
import com.woowacourse.dsgram.service.ArticleService;
import com.woowacourse.dsgram.service.FileService;
import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.ArticleRequest;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.vo.FileInfo;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {

    private UserService userService;
    private ArticleService articleService;
    private FileService fileService;

    public ArticleApiController(UserService userService, ArticleService articleService, FileService fileService) {
        this.userService = userService;
        this.articleService = articleService;
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<Article> create(ArticleRequest articleRequest, @UserSession LoggedInUser loggedInUser) {
        FileInfo fileInfo = fileService.save(articleRequest.getFile(), new ArticleFileNamingStrategy());
        Article article = convertFrom(articleRequest, fileInfo, loggedInUser);
        Article savedArticle = articleService.create(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.OK);
    }

    @GetMapping("{articleId}/file")
    public ResponseEntity<byte[]> showArticleFile(@PathVariable long articleId) {
        Article article = articleService.findById(articleId);
        byte[] base64 = fileService.readFile(article);

        return new ResponseEntity<>(base64, HttpStatus.OK);
    }

    @PutMapping("{articleId}")
    public ResponseEntity update(@PathVariable long articleId, @RequestBody ArticleEditRequest articleEditRequest, @UserSession LoggedInUser loggedInUser) {
        articleService.update(articleId, articleEditRequest, loggedInUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{articleId}")
    public ResponseEntity delete(@PathVariable long articleId, @UserSession LoggedInUser loggedInUser) {
        articleService.delete(articleId, loggedInUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    private Article convertFrom(ArticleRequest articleRequest, FileInfo fileInfo, @UserSession LoggedInUser loggedInUser) {
        User user = userService.findUserById(loggedInUser.getId());
        return new Article(articleRequest.getContents(), fileInfo.getFileName(), fileInfo.getFilePath(), user);
    }
}
