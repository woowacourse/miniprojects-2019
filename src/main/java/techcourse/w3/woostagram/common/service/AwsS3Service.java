package techcourse.w3.woostagram.common.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.article.exception.FileSaveFailException;
import techcourse.w3.woostagram.article.exception.InvalidExtensionException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AwsS3Service implements StorageService {
    private static final String AWS_S3_URL = "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com";
    private static final String BUCKET_NAME = "woowahan-crews";
    private static final List<String> VALID_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");
    private static final String PATH_DELIMITER = ".";
    private final AmazonS3 s3;

    public AwsS3Service() {
        this.s3 = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.AP_NORTHEAST_2)
            .build();
    }

    @Override
    public String saveMultipartFile(MultipartFile multipartFile) {
        String fileExtension = validateFileExtension(multipartFile.getOriginalFilename());
        String fileName = String.join(PATH_DELIMITER, UUID.randomUUID().toString(), fileExtension);
        File file = new File(fileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            s3.putObject(BUCKET_NAME, fileName, file);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (IOException e) {
            throw new FileSaveFailException();
        } finally {
            file.delete();
        }
        return String.join("/", AWS_S3_URL, fileName);
    }

    private String validateFileExtension(String filename) {
        String fileExtension = FilenameUtils.getExtension(filename);
        if (fileExtension == null || !VALID_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new InvalidExtensionException();
        }
        return fileExtension;
    }
}
