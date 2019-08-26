package techcourse.w3.woostagram.common.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.article.exception.FileSaveFailException;
import techcourse.w3.woostagram.article.exception.InvalidExtensionException;
import techcourse.w3.woostagram.common.support.AwsS3Properties;
import techcourse.w3.woostagram.common.support.ImageResizeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AwsS3Service implements StorageService {
    private static final List<String> VALID_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");
    private static final String PATH_DELIMITER = ".";
    private final String AWS_S3_URL;
    private final String BUCKET_NAME;
    private final AmazonS3 s3;

    @Autowired
    public AwsS3Service(AwsS3Properties awsS3Properties, AmazonS3 s3) {
        AWS_S3_URL = awsS3Properties.getUrl();
        BUCKET_NAME = awsS3Properties.getBucket();
        this.s3 = s3;
    }

    @Override
    public String saveMultipartFile(MultipartFile multipartFile) {
        String fileExtension = validateFileExtension(multipartFile.getOriginalFilename());
        String fileName = String.join(PATH_DELIMITER, UUID.randomUUID().toString(), fileExtension);
        File file = new File(fileName);
        File resizedFile = new File(fileName + "_resized");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            ImageResizeUtils.imageResize(fileExtension, file, resizedFile);
            s3.putObject(BUCKET_NAME, fileName, resizedFile);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        } catch (IOException e) {
            throw new FileSaveFailException();
        } finally {
            file.delete();
            resizedFile.delete();
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

    @Override
    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.split(AWS_S3_URL + "/")[1];
        try {
            s3.deleteObject(BUCKET_NAME, fileName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}
