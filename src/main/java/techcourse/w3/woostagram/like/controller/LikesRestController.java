package techcourse.w3.woostagram.like.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.like.service.LikesService;
import techcourse.w3.woostagram.user.support.LoggedInUser;

@RestController
@RequestMapping("/api/like")
public class LikesRestController {
    private final LikesService likesService;

    public LikesRestController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/{articleId}")
    public ResponseEntity create(@PathVariable Long articleId, @LoggedInUser String email) {
        likesService.add(email, articleId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
