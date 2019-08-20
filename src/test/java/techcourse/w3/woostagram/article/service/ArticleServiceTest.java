package techcourse.w3.woostagram.article.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.domain.ArticleRepository;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.exception.ArticleNotFoundException;
import techcourse.w3.woostagram.article.exception.InvalidExtensionException;
import techcourse.w3.woostagram.common.service.StorageService;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    private static final String USER_EMAIL = "moomin@naver.com";
    @InjectMocks
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserService userService;
    @Mock
    private StorageService storageService;
    private Article article;
    private User user;

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
    void save_correctContentsAndImage_isOk() {
        MultipartFile multipartFile = new MockMultipartFile("testImage", "testImage.jpg", MediaType.IMAGE_JPEG_VALUE, "<<jpg data>>".getBytes());
        ArticleDto articleDto = ArticleDto.builder()
                .contents("Test article")
                .imageFile(multipartFile)
                .build();

        when(storageService.saveMultipartFile(multipartFile)).thenReturn("aaminiprojects-2019bb");
        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);
        when(articleRepository.save(article)).thenReturn(article);
        articleService.save(articleDto, USER_EMAIL);
        verify(articleRepository).save(article);
    }

    @Test
    void save_wrongExtension_exception() {
        MultipartFile multipartFile = new MockMultipartFile("testImage", "testImage.xyz", MediaType.IMAGE_JPEG_VALUE, "<<jpg data>>".getBytes());

        ArticleDto articleDto = ArticleDto.builder()
                .contents("Test article")
                .imageFile(multipartFile)
                .build();
        when(storageService.saveMultipartFile(multipartFile)).thenThrow(InvalidExtensionException.class);
        assertThrows(InvalidExtensionException.class, () -> articleService.save(articleDto, USER_EMAIL));
    }

    @Test
    void get_correctArticleId_isOk() {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        articleService.findById(1L);
        verify(articleRepository).findById(1L);
    }

    @Test
    void get_incorrectArticleId_exception() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ArticleNotFoundException.class, () -> articleService.findById(anyLong()));
    }

    @Test
    void update_incorrectArticleId_exception() {
        ArticleDto articleDto = ArticleDto.builder()
                .id(1L)
                .contents("Test article")
                .build();
        when(articleRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ArticleNotFoundException.class, () -> articleService.update(articleDto));
    }

    @Test
    void update_correctArticleId_exception() {
        ArticleDto articleDto = ArticleDto.builder()
                .id(1L)
                .contents("Test article")
                .build();
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));
        assertDoesNotThrow(() -> articleService.update(articleDto));
    }

    @Test
    void delete_correctArticleId_isOk() {
        doNothing().when(articleRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> articleService.deleteById(anyLong()));
    }
}