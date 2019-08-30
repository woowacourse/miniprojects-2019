package techcourse.w3.woostagram.mypage.service;

import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.follow.service.FollowService;
import techcourse.w3.woostagram.mypage.dto.UserPageDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

@Service
public class MypageService {
    private final UserService userService;
    private final FollowService followService;

    public MypageService(UserService userService, FollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    public UserPageDto findUserPageDto(String loginEmail, String userName) {
        UserInfoDto pageUser = userService.findByUserName(userName);
        return UserPageDto.from(pageUser,
                userService.findByEmail(loginEmail),
                followService.checkFollowing(loginEmail, pageUser.getId())
        );
    }
}
