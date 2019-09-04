package techcourse.w3.woostagram.follow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import techcourse.w3.woostagram.follow.service.FollowService;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.List;

@RestController
@RequestMapping("/api/users/{targetId}/follows")
public class FollowRestController {
    private final FollowService followService;

    public FollowRestController(final FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/followers")
    public ResponseEntity<List<UserInfoDto>> listFollowers(@PathVariable Long targetId) {
        return ResponseEntity.ok(followService.findAllByTo(targetId));
    }

    @GetMapping("/followings")
    public ResponseEntity<List<UserInfoDto>> listFollowing(@PathVariable Long targetId) {
        return ResponseEntity.ok(followService.findAllByFrom(targetId));
    }

    @PostMapping
    public ResponseEntity create(@PathVariable Long targetId, @LoggedInUser String email) {
        followService.save(email, targetId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity delete(@PathVariable Long targetId, @LoggedInUser String email) {
        followService.delete(email, targetId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers/num")
    public ResponseEntity<Integer> listNumberOfFollowers(@PathVariable Long targetId) {
        return ResponseEntity.ok(followService.findAllByTo(targetId).size());
    }

    @GetMapping("/followings/num")
    public ResponseEntity<Integer> listNumberOfFollowing(@PathVariable Long targetId) {
        return ResponseEntity.ok(followService.findAllByFrom(targetId).size());
    }
}
