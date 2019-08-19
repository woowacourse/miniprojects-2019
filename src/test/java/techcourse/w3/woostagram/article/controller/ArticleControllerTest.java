package techcourse.w3.woostagram.article.controller;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.article.dto.ArticleDto;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleControllerTest extends AbstractControllerTests {
    @Test
    void create_correctArticle_isOk() {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("imageFile", new ByteArrayResource("<<png data>>".getBytes()) {
            @Override
            public String getFilename() {
                return "test_image.jpg";
            }
        }, MediaType.IMAGE_JPEG);

        assertThat(postMultipartRequest("/articles", ArticleDto.class, bodyBuilder, "", "hello", "", "", "").getStatus().is3xxRedirection()).isTrue();
    }

    @Test
    void createForm_noState_isOk() {
        assertThat(getRequest("/articles/form").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void show_correctArticleId_isOk() {
        assertThat(getRequest("/articles/1").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void delete_correctArticleId_isOk() {
        assertThat(deleteRequest("/articles/1").getStatus().is3xxRedirection()).isTrue();
    }

    @Test
    void delete_incorrectArticleId_isNotFound() {
        assertThat(deleteRequest("/articles/2").getStatus().is4xxClientError()).isTrue();
    }
}