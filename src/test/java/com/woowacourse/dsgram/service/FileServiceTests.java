package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.exception.NotFoundFileException;
import com.woowacourse.dsgram.service.vo.FileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class FileServiceTests {

    private FileService fileService = new FileService();
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        multipartFile = new MockMultipartFile("testImage", "testImage.jpg", MediaType.IMAGE_JPEG_VALUE, "jpgFile".getBytes());
    }

    @Test
    @DisplayName("파일 생성 성공 테스트")
    void save() {
        assertThat(fileService.save(multipartFile)).isNotNull();

    }

    @Test
    @DisplayName("파일 조회 성공 테스트")
    void read() {
        FileInfo fileInfo = fileService.save(multipartFile);
        assertThat(fileService.readFile(new Article("contents", fileInfo.getFileName(), fileInfo.getFilePath()))).isNotEmpty();
    }

    @Test
    @DisplayName("파일 조회 실패 테스트")
    void readFail() {
        assertThrows(NotFoundFileException.class, () -> {
            fileService.readFile(new Article("contents", "fileName", "notExistPath"));
        });
    }

}
