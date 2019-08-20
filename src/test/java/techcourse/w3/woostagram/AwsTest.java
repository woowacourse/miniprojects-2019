package techcourse.w3.woostagram;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AwsTest {
    private AmazonS3 s3;
    private String bucketName;
    private String fileName;
    private String fileContent;

    @BeforeEach
    void setup() {
        bucketName = "woowahan-crews";
        fileName = "moomin.png";
        fileContent = "moomin";

        s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Test
    void AwsUploadTest() {
        assertDoesNotThrow(() -> s3.putObject(bucketName, fileName, fileContent));
    }

    @Test
    void AwsDeleteTest() {
        assertDoesNotThrow(() -> s3.deleteObject(bucketName, fileName));
    }

    @Test
    void AwsGetTest() {
        String getTestFileName = "test.jpg";
        assertDoesNotThrow(() -> s3.putObject(bucketName, getTestFileName, fileContent));
        assertDoesNotThrow(() -> s3.getObject(bucketName, getTestFileName));
        assertDoesNotThrow(() -> s3.deleteObject(bucketName, getTestFileName));

    }

    @Test
    void AwsPostImageTest() throws IOException {
        URL url = new URL("https://pbs.twimg.com/profile_images/631800387016626176/P5jbtPH5.png");
        File file = new File("downloaded");
        FileUtils.copyURLToFile(url, file);

        assertDoesNotThrow(() -> s3.putObject(bucketName, fileName, file));
        file.delete();
    }
}
