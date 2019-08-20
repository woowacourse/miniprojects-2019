package techcourse.w3.woostagram.follow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.follow.service.FollowService;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.support.LoggedInUser;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowRestController {
    private final FollowService followService;

    public FollowRestController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/from/{userId}")
    public ResponseEntity<List<UserInfoDto>> readFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    @GetMapping("/to/{userId}")
    public ResponseEntity<List<UserInfoDto>> readFollowing(@PathVariable Long userId) {
        List<UserInfoDto> dtos = followService.getFollowing(userId);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{userId}")
    public ResponseEntity create(@PathVariable Long userId, @LoggedInUser String email) {
        followService.add(email, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity delete(@PathVariable Long userId, @LoggedInUser String email) {
        followService.remove(email, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/num/from/{userId}")
    public ResponseEntity<Integer> readNumberOfFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId).size());
    }

    @GetMapping("/num/to/{userId}")
    public ResponseEntity<Integer> readNumberOfFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowing(userId).size());
    }
}
