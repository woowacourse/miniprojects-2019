package techcourse.w3.woostagram.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.comment.domain.CommentRepository;
import techcourse.w3.woostagram.comment.dto.CommentDto;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    private static final String USER_EMAIL = "commentTest@naver.com";
    private static final Long ARTICLE_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long COMMENT_ID = 1L;

    @InjectMocks
    private CommentService commentService;
    @Mock
    private UserService userService;
    @Mock
    private ArticleService articleService;
    @Mock
    private CommentRepository commentRepository;

    private User user;
    private Article article;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .userContents(UserContents.builder()
                        .userName("moomin")
                        .build())
                .email(USER_EMAIL)
                .password("qweQWE123!@#")
                .build();

        article = Article.builder()
                .contents("Test article")
                .imageUrl("/home/yumin/Codes/WoowaTech/Level2/miniprojects-2019/src/main/resources/static/uploaded/testImage.jpg")
                .user(user)
                .build();
    }

    @Test
    void save_correct_ok() {
        CommentDto commentDto = CommentDto.builder().build();

        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);
        when(articleService.findArticleById(ARTICLE_ID)).thenReturn(article);
        when(commentRepository.save(commentDto.toEntity(user, article))).thenReturn(commentDto.toEntity(user, article));
        assertThat(commentService.save(commentDto, USER_EMAIL, ARTICLE_ID)).isEqualTo(CommentDto.from(commentDto.toEntity(user, article), USER_ID));
    }


    @Test
    void findByArticleId_correct_ok() {
        CommentDto commentDto1 = CommentDto.builder().build();
        CommentDto commentDto2 = CommentDto.builder().build();

        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);
        when(commentRepository.findByArticle_Id(ARTICLE_ID)).thenReturn(Arrays.asList(
                commentDto1.toEntity(user, article),
                commentDto2.toEntity(user, article)
        ));

        assertThat(commentService.findByArticleId(ARTICLE_ID, USER_EMAIL)).isEqualTo(Arrays.asList(
                CommentDto.from(commentDto1.toEntity(user, article), USER_ID),
                CommentDto.from(commentDto2.toEntity(user, article), USER_ID)
        ));


    }

    @Test
    void deleteById_correct_ok() {
        CommentDto commentDto = CommentDto.builder().build();

        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);
        when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(commentDto.toEntity(user, article)));

        commentService.deleteById(COMMENT_ID, USER_EMAIL);
        verify(commentRepository, times(1)).deleteById(COMMENT_ID);
        verify(userService, times(1)).findUserByEmail(USER_EMAIL);
        verify(commentRepository, times(1)).findById(COMMENT_ID);
    }
}