package techcourse.w3.woostagram.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loggedin")
    public ResponseEntity<UserInfoDto> readLoginInformation(@LoggedInUser String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<String> uploadProfileImage(MultipartFile imageFile,
                                                     @LoggedInUser String email) {
        String imageUrl = userService.uploadProfileImage(imageFile, email);
        return ResponseEntity.ok(imageUrl);
    }

    @DeleteMapping
    public ResponseEntity deleteProfileImage(@LoggedInUser String email) {
        String imageUrl = userService.deleteProfileImage(email);
        return ResponseEntity.ok(imageUrl);
    }
}
