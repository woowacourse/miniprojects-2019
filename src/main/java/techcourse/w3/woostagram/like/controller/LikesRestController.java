package techcourse.w3.woostagram.like.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.like.service.LikesService;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.common.support.LoggedInUser;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
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

    @GetMapping("/{articleId}")
    public ResponseEntity<List<UserInfoDto>> readLikedUsers(@PathVariable Long articleId) {
        return ResponseEntity.ok(likesService.getLikedUser(articleId));
    }

    @DeleteMapping("/{articleId}/{likedId}")
    public ResponseEntity delete(@PathVariable Long articleId, @PathVariable Long likedId) {
        likesService.remove(articleId, likedId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/num/{articleId}")
    public ResponseEntity<Integer> readNumberOfLiked(@PathVariable Long articleId) {
        return ResponseEntity.ok(likesService.getLikedUser(articleId).size());
    }
}
