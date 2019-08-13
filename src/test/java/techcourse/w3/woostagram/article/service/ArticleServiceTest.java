package techcourse.w3.woostagram.article.service;

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks
    ArticleService articleService;
    @Mock
    ArticleRepository articleRepository;

    @Test
    void save_correctContentsAndImage_isOk() {
        Article article = Article.builder()
                .contents("Test article")
                .imageUrl("/home/yumin/Codes/WoowaTech/Level2/miniprojects-2019/src/main/resources/static/testImage.jpg")
                .build();

        MultipartFile multipartFile = new MockMultipartFile("testImage", "testImage.jpg", MediaType.IMAGE_JPEG_VALUE, "<<jpg data>>".getBytes());

        ArticleDto articleDto = ArticleDto.builder()
                .contents("Test article")
                .imageFile(multipartFile)
                .build();

        when(articleRepository.save(article)).thenReturn(article);
        articleService.save(articleDto);
        verify(articleRepository).save(article);
    }
}