package techcourse.w3.woostagram.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.user.dto.UserProfileImageDto;
import techcourse.w3.woostagram.user.service.UserService;
import techcourse.w3.woostagram.common.support.LoggedInUser;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> uploadProfileImage(UserProfileImageDto userProfileImageDto,
                                                     @LoggedInUser String email) {
        String imageUrl = userService.uploadProfileImage(userProfileImageDto, email);
        return ResponseEntity.ok(imageUrl);
    }
}
