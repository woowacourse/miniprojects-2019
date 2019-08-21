package com.wootecobook.turkey.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.wootecobook.turkey.config.AwsMockConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static com.wootecobook.turkey.config.AwsMockConfig.S3MOCK_ENDPOINT;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(AwsMockConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class S3ConnectorTest {

    @Autowired
    private S3Connector s3Connector;

    @Autowired
    private AmazonS3 mockS3Client;

    @Autowired
    private String bucket;

    private MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "file", "mock1.png", "image/png", "test data".getBytes());
    private String key = "savedFileName";

    @Test
    void 파일_업로드_테스트() throws IOException {
        //when
        String savedUrl = s3Connector.upload(mockMultipartFile, key);

        // then
        String resourceUrl = String.format("%s/%s/%s", S3MOCK_ENDPOINT, bucket, key);
        assertThat(savedUrl).isEqualTo(resourceUrl);
    }

    @Test
    void 파일_삭제_테스트() throws IOException {
        //given
        s3Connector.upload(mockMultipartFile, key);
        assertDoesNotThrow(() -> mockS3Client.getObject(bucket, key));

        //when
        s3Connector.delete(key);

        //then
        assertThrows(AmazonS3Exception.class, () -> mockS3Client.getObject(bucket, key));
    }
}