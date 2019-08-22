package techcourse.w3.woostagram.follow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import techcourse.w3.woostagram.follow.domain.Follow;
import techcourse.w3.woostagram.follow.domain.FollowRepository;
import techcourse.w3.woostagram.follow.exception.FollowNotFoundException;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
    @InjectMocks
    private FollowService followService;
    @Mock
    private UserService userService;
    @Mock
    private FollowRepository followRepository;

    private User follower;
    private User followee;
    private Follow follow;

    @BeforeEach
    void setUp() {
        follower = User.builder()
                .id(1L)
                .userContents(UserContents.builder()
                        .userName("moomin1")
                        .build())
                .email("a@naver.com")
                .password("qweQWE123!@#")
                .build();

        followee = User.builder()
                .id(2L)
                .userContents(UserContents.builder()
                        .userName("moomin2")
                        .build())
                .email("b@naver.com")
                .password("qweQWE123!@#")
                .build();

        follow = Follow.builder()
                .from(follower)
                .to(followee)
                .build();
    }

    @Test
    void save_correctId_success() {
        when(userService.findUserByEmail(anyString())).thenReturn(follower);
        when(userService.findById(anyLong())).thenReturn(followee);
        when(followRepository.save(any())).thenReturn(follow);
        followService.save(follower.getEmail(), followee.getId());
        verify(followRepository).save(follow);
    }

    @Test
    void findAllByTo_correctId_success() {
        List<UserInfoDto> followees = Stream.of(follow)
                .map(follow -> UserInfoDto.from(follow.getFrom()))
                .collect(Collectors.toList());

        when(userService.findById(anyLong())).thenReturn(follower);
        when(followRepository.findAllByTo(any())).thenReturn(Arrays.asList(follow));
        assertThat(followService.findAllByTo(anyLong())).isEqualTo(followees);
    }

    @Test
    void findAllByFrom_correctId_success() {
        Follow reverseFollow = Follow.builder()
                .from(followee)
                .to(follower)
                .build();

        List<UserInfoDto> followers = Stream.of(follow)
                .map(follow -> UserInfoDto.from(follow.getFrom()))
                .collect(Collectors.toList());

        when(userService.findById(anyLong())).thenReturn(followee);
        when(followRepository.findAllByFrom(any())).thenReturn(Arrays.asList(reverseFollow));
        assertThat(followService.findAllByFrom(anyLong())).isEqualTo(followers);
    }

    @Test
    void delete_correctId_success() {
        when(userService.findUserByEmail(anyString())).thenReturn(follower);
        when(userService.findById(anyLong())).thenReturn(followee);
        when(followRepository.findByFromAndTo(any(), any())).thenReturn(Optional.of(follow));
        followService.delete(follower.getEmail(), followee.getId());
        verify(followRepository).delete(follow);
    }

    @Test
    void delete_wrongId_exception() {
        when(userService.findUserByEmail(anyString())).thenReturn(follower);
        when(userService.findById(anyLong())).thenReturn(followee);
        when(followRepository.findByFromAndTo(any(), any())).thenThrow(FollowNotFoundException.class);
        assertThrows(FollowNotFoundException.class, () ->
                followService.delete(follower.getEmail(), followee.getId()));
    }
}