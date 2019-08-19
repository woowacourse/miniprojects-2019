package techcourse.w3.woostagram;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AwsTest {
    private AmazonS3 s3;
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private String fileName;
    private String fileContent;

    @BeforeEach
    void setUp() {
        bucketName = "woowahan-crews";
        accessKey = "AKIA4IVPY73FVPHOTMEH";
        secretKey = "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";
        fileName = "moomin.png";
        fileContent = "moomin";

        s3 = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                    accessKey,
                    secretKey
            )))
            .withRegion(Regions.AP_NORTHEAST_2)
            .build();
    }

    @Test
    void AwsUploadTest() {
        try {
            s3.putObject(bucketName, fileName, fileContent);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    @Test
    void AwsDeleteTest() {
        try {
            s3.deleteObject(bucketName, fileName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    @Test
    void AwsGetTest() {
        try {
            s3.getObject(bucketName, fileName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    @Test
    void AwsPostImageTest() throws IOException {
        URL url = new URL("https://pbs.twimg.com/profile_images/631800387016626176/P5jbtPH5.png");
        File file = new File("downloaded");
        FileUtils.copyURLToFile(url, file);

        try {
            s3.putObject(bucketName, fileName, file);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

        file.delete();
    }
}
