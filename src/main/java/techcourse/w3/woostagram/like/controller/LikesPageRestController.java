package techcourse.w3.woostagram.like.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import techcourse.w3.woostagram.like.service.LikesService;
import techcourse.w3.woostagram.main.dto.MainArticleDto;

@RestController
@RequestMapping("/api/likes")
public class LikesPageRestController {
    private final LikesService likesService;

    public LikesPageRestController(LikesService likesService) {
        this.likesService = likesService;
    }

    @GetMapping
    public ResponseEntity<Page<MainArticleDto>> read(Pageable pageable, @LoggedInUser String email) {
        return ResponseEntity.ok(likesService.findLikesArticle(email, pageable));
    }
}
