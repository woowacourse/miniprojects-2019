package techcourse.w3.woostagram.search.service;

import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.List;

@Service
public class UserSearchService implements SearchService<UserInfoDto> {

    private final UserService userService;

    public UserSearchService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserInfoDto> search(String query) {
        return userService.findByUsernameContaining(query);
    }
}
