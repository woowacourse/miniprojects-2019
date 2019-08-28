package techcourse.w3.woostagram.article.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.common.support.TestDataInitializer;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleControllerTest extends AbstractControllerTests {
    @Test
    void create_correctArticle_isOk() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/rohan-varma/rohan-blog/master/images/mnistimg.png");
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("imageFile", new ByteArrayResource(IOUtils.toByteArray(url)) {
            @Override
            public String getFilename() {
                return "test_image.jpg";
            }
        }, MediaType.IMAGE_JPEG);
        bodyBuilder.part("contents", "Moomin contents");

        assertThat(postMultipartRequest("/articles", bodyBuilder.build()).getStatus().is3xxRedirection()).isTrue();
    }

    @Test
    void createForm_noState_isOk() {
        assertThat(getRequest("/articles/form").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void show_correctArticleId_isOk() {
        assertThat(getRequest("/articles/" + TestDataInitializer.basicArticle.getId()).getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void delete_correctArticleId_isOk() {
        assertThat(deleteRequest("/articles/" + TestDataInitializer.deleteArticle.getId()).getStatus().is3xxRedirection()).isTrue();
    }

    @Test
    void delete_incorrectArticleId_isNotFound() {
        assertThat(deleteRequest("/articles/11231").getStatus().is4xxClientError()).isTrue();
    }
}