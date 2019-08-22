package techcourse.w3.woostagram.follow.service;

import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.follow.domain.Follow;
import techcourse.w3.woostagram.follow.domain.FollowRepository;
import techcourse.w3.woostagram.follow.exception.FollowNotFoundException;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    public FollowService(final UserService userService, final FollowRepository followRepository) {
        this.userService = userService;
        this.followRepository = followRepository;
    }

    @Transactional
    public void add(String email, Long targetId) {
        User user = userService.findUserByEmail(email);
        User targetUser = userService.findById(targetId);
        Follow follow = Follow.builder()
                .from(user)
                .to(targetUser)
                .build();
        followRepository.save(follow);
    }

    public List<UserInfoDto> getFollowers(Long id) {
        User user = userService.findById(id);
        List<Follow> followers = followRepository.findAllByTo(user);
        return followers.stream()
                .map(follow -> UserInfoDto.from(follow.getFrom()))
                .collect(Collectors.toList());
    }

    public List<UserInfoDto> getFollowing(Long id) {
        User user = userService.findById(id);
        List<Follow> followers = followRepository.findAllByFrom(user);
        return followers.stream()
                .map(follow -> UserInfoDto.from(follow.getTo()))
                .collect(Collectors.toList());
    }

    public void remove(String email, Long targetId) {
        User user = userService.findUserByEmail(email);
        User targetUser = userService.findById(targetId);
        Follow following = followRepository.findByFromAndTo(user, targetUser).orElseThrow(FollowNotFoundException::new);
        following.nullify();
        followRepository.delete(following);
    }
}
