package techcourse.w3.woostagram.common.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.article.exception.FileDeleteFailException;
import techcourse.w3.woostagram.article.exception.FileSaveFailException;
import techcourse.w3.woostagram.article.exception.InvalidExtensionException;
import techcourse.w3.woostagram.common.support.AwsS3Properties;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwsS3ServiceTest {
    @InjectMocks
    private AwsS3Service awsS3Service;

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    private AwsS3Properties awsS3Properties;

    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/rohan-varma/rohan-blog/master/images/mnistimg.png");
        multipartFile = new MockMultipartFile("testImage",
                "testImage.png", MediaType.IMAGE_PNG_VALUE, IOUtils.toByteArray(url));
    }

    @Test
    void saveMultipartFile_correctMultipartFile_isOk() {
        when(awsS3Properties.getBucket()).thenReturn("mybucket");
        when(amazonS3.putObject(anyString(), anyString(), any(File.class))).thenReturn(null);

        awsS3Service.saveMultipartFile(multipartFile);

        verify(amazonS3, times(1)).putObject(anyString(), anyString(), any(File.class));
        verify(awsS3Properties, times(1)).getUrl();
    }

    @Test
    void saveMultipartFile_incorrect_FileSaveFailException() {
        when(awsS3Properties.getBucket()).thenReturn("mybucket");
        when(amazonS3.putObject(anyString(), anyString(), any(File.class))).thenThrow(AmazonServiceException.class);

        assertThrows(FileSaveFailException.class, () -> awsS3Service.saveMultipartFile(multipartFile));
    }

    @Test
    void saveMultipartFile_incorrectFileExtension_InvalidExtensionException() {
        multipartFile = new MockMultipartFile("testImage",
                "testImage.mp4", MediaType.APPLICATION_JSON.getType(), "txt.png".getBytes());

        assertThrows(InvalidExtensionException.class, () -> awsS3Service.saveMultipartFile(multipartFile));
    }

    @Test
    void deleteFile_correct_isOk() {
        when(awsS3Properties.getBucket()).thenReturn("mybucket");
        when(awsS3Properties.getUrl()).thenReturn("myurl");
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());

        awsS3Service.deleteFile("myurl/myfile.jpg");

        verify(awsS3Properties, times(1)).getBucket();
    }

    @Test
    void deleteFile_incorrect_FileDeleteFailException() {
        when(awsS3Properties.getBucket()).thenReturn("mybucket");
        when(awsS3Properties.getUrl()).thenReturn("myurl");

        doThrow(AmazonServiceException.class).when(amazonS3).deleteObject(anyString(), anyString());

        assertThrows(FileDeleteFailException.class, () -> awsS3Service.deleteFile("myurl/myfile.jpg"));
    }
}