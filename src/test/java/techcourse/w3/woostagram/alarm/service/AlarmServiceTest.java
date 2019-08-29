package techcourse.w3.woostagram.alarm.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {
    @InjectMocks
    private AlarmService alarmService;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    private User user1;
    private User user2;
    private Article article;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1L)
                .userContents(UserContents.builder()
                        .userName("moomin")
                        .build())
                .email("moomin@naver.com")
                .password("qweQWE123!@#")
                .build();

        user2 = User.builder()
                .id(2L)
                .userContents(UserContents.builder()
                        .userName("iva")
                        .build())
                .email("iva@naver.com")
                .password("qweQWE123!@#")
                .build();

        article = Article.builder()
                .contents("Test article")
                .imageUrl("/home/yumin/Codes/WoowaTech/Level2/miniprojects-2019/src/main/resources/static/uploaded/testImage.jpg")
                .user(user1)
                .build();

        doNothing().when(simpMessagingTemplate).convertAndSend(anyString(), anyString());
    }

    @Test
    void pushLikes_correct_isOk() {
        alarmService.pushLikes(user1, article);

        verify(simpMessagingTemplate, times(1)).convertAndSend(anyString(), anyString());
    }

    @Test
    void pushFollows_correct_isOk() {
        alarmService.pushFollows(user1, user2);

        verify(simpMessagingTemplate, times(1)).convertAndSend(anyString(), anyString());
    }
}