package techcourse.w3.woostagram.user.controller;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import techcourse.w3.woostagram.AbstractControllerTests;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRestControllerTest extends AbstractControllerTests {
    @Test
    void uploadProfileImage_correctProfileImage_isOk() throws IOException {
        URL url = new URL("https://comicvine1.cbsistatic.com/uploads/scale_small/0/77/802890-glad_mumin.jpg");
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("imageFile", new ByteArrayResource(IOUtils.toByteArray(url)) {
            @Override
            public String getFilename() {
                return "test_image.jpg";
            }
        }, MediaType.IMAGE_JPEG);

        assertThat(postMultipartRequest("/api/users", bodyBuilder.build()).getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void uploadProfileImage_wrongFileImage_isFail() {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("imageFile", new ByteArrayResource(new byte[0]) {
            @Override
            public String getFilename() {
                return "";
            }
        }, MediaType.IMAGE_JPEG);

        assertThat(postMultipartRequest("/api/users", bodyBuilder.build()).getStatus().is5xxServerError()).isTrue();
    }

    @Test
    void uploadProfileImage_wrongFileExtension_isFail() {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("imageFile", new ByteArrayResource(new byte[0]) {
            @Override
            public String getFilename() {
                return "";
            }
        });

        assertThat(postMultipartRequest("/api/users", bodyBuilder.build()).getStatus().is5xxServerError()).isTrue();
    }

    @Test
    void readLoginInformation_correctLoggedInUserId_isOk() {
        assertThat(getRequest("/api/users/loggedin").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void delete_correct_isOk() {
        assertThat(deleteRequest("api/users").getStatus().is2xxSuccessful()).isTrue();
    }
}
