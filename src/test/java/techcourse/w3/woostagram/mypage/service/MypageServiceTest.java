//package techcourse.w3.woostagram.mypage.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import techcourse.w3.woostagram.article.domain.Article;
//import techcourse.w3.woostagram.article.service.ArticleService;
//import techcourse.w3.woostagram.comment.service.CommentService;
//import techcourse.w3.woostagram.follow.service.FollowService;
//import techcourse.w3.woostagram.like.service.LikesService;
//import techcourse.w3.woostagram.user.domain.User;
//import techcourse.w3.woostagram.user.domain.UserContents;
//import techcourse.w3.woostagram.user.dto.UserInfoDto;
//import techcourse.w3.woostagram.user.service.UserService;
//
//import java.util.Arrays;
//
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class MypageServiceTest {
//    @InjectMocks
//    private MypageService mypageService;
//
//    @Mock
//    private ArticleService articleService;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private CommentService commentService;
//
//    @Mock
//    private LikesService likesService;
//
//    @Mock
//    private FollowService followService;
//
//    private User user;
//    private Article article;
//
//    @BeforeEach
//    void setUp() {
//        user = User.builder()
//                .id(1L)
//                .userContents(UserContents.builder()
//                        .userName("moomin")
//                        .build())
//                .email("moomin@naver.com")
//                .password("qweQWE123!@#")
//                .build();
//
//        article = Article.builder()
//                .contents("Test article")
//                .imageUrl("/home/yumin/Codes/WoowaTech/Level2/miniprojects-2019/src/main/resources/static/uploaded/testImage.jpg")
//                .user(user)
//                .build();
//    }
//
//    @Test
//    void getMypageArticles_correctUserNameAndPage_isOk() {
//        when(userService.findUserByUserName(anyString())).thenReturn(user);
//        when(articleService.findPageByUsers(anyList(), PageRequest.of(0, 1))).thenReturn(new PageImpl<>(Arrays.asList(article), PageRequest.of(0, 1), 1));
//        when(likesService.findLikedUserByArticleId(article.getId())).thenReturn(Arrays.asList(UserInfoDto.from(user)));
//        when(commentService.countByArticleId(article.getId())).thenReturn(1);
//
//
//    }
//}