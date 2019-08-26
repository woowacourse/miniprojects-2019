package techcourse.w3.woostagram.like.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import techcourse.w3.woostagram.alarm.service.AlarmService;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.like.domain.Likes;
import techcourse.w3.woostagram.like.domain.LikesRepository;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikesServiceTest {
    @InjectMocks
    private LikesService likesService;

    @Mock
    private LikesRepository likesRepository;

    @Mock
    private UserService userService;

    @Mock
    private ArticleService articleService;

    @Mock
    private AlarmService alarmService;

    private User user;
    private Article article;
    private Likes likes;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).email("a@naver.com").userContents(UserContents.builder().build()).build();
        article = Article.builder().user(user).build();
        likes = Likes.builder().article(article).user(user).build();
    }

    @Test
    void save_correct_ok() {
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(articleService.findArticleById(article.getId())).thenReturn(article);
        when(likesRepository.save(likes)).thenReturn(likes);

        likesService.save(user.getEmail(), article.getId());

        verify(userService, times(1)).findUserByEmail(user.getEmail());
        verify(articleService, times(1)).findArticleById(article.getId());
        verify(likesRepository, times(1)).save(likes);
    }

    @Test
    void findLikedUserByArticleId_correct_ok() {
        when(articleService.findArticleById(article.getId())).thenReturn(article);
        when(likesRepository.findAllByArticle(article)).thenReturn(Arrays.asList(likes, likes));

        assertThat(likesService.findLikedUserByArticleId(article.getId())).isEqualTo(Arrays.asList(
                UserInfoDto.from(likes.getUser()),
                UserInfoDto.from(likes.getUser())
        ));
    }

    @Test
    void remove_correct_ok() {
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(articleService.findArticleById(article.getId())).thenReturn(article);
        when(likesRepository.findByArticleAndUser_Id(article, user.getId())).thenReturn(likes);

        likesService.delete(article.getId(), user.getEmail());

        verify(userService, times(1)).findUserByEmail(user.getEmail());
        verify(articleService, times(1)).findArticleById(article.getId());
        verify(likesRepository, times(1)).findByArticleAndUser_Id(article, user.getId());
        verify(likesRepository, times(1)).delete(likes);
    }
}