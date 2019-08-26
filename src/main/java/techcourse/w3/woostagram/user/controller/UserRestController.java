package techcourse.w3.woostagram.user.controller;

import org.springframework.http.ResponseEntity;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.dto.UserProfileImageDto;
import techcourse.w3.woostagram.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loggedin")
    public ResponseEntity<UserInfoDto> readLoginInformation(@LoggedInUser String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<String> uploadProfileImage(UserProfileImageDto userProfileImageDto,
                                                     @LoggedInUser String email) {
        String imageUrl = userService.uploadProfileImage(userProfileImageDto, email);
        return ResponseEntity.ok(imageUrl);
    }

    @DeleteMapping
    public ResponseEntity deleteProfileImage(@LoggedInUser String email) {
        userService.deleteProfileImage(email);
        return ResponseEntity.ok().build();
    }
}
