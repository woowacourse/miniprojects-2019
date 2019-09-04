package techcourse.w3.woostagram.common.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.article.exception.FileDeleteFailException;
import techcourse.w3.woostagram.article.exception.FileSaveFailException;
import techcourse.w3.woostagram.article.exception.InvalidExtensionException;
import techcourse.w3.woostagram.common.exception.EmptyFileException;
import techcourse.w3.woostagram.common.support.AwsS3Properties;

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

    private final AmazonS3 s3;
    private final AwsS3Properties awsS3Properties;
    private final ImageResizeService imageResizeService;

    @Autowired
    public AwsS3Service(final AwsS3Properties awsS3Properties, final AmazonS3 s3, final ImageResizeService imageResizeService) {
        this.awsS3Properties = awsS3Properties;
        this.s3 = s3;
        this.imageResizeService = imageResizeService;
    }

    @Override
    public String saveMultipartFile(MultipartFile multipartFile) {
        validateNullFile(multipartFile);
        String fileExtension = validateFileExtension(multipartFile.getOriginalFilename());
        String fileName = String.join(PATH_DELIMITER, UUID.randomUUID().toString(), fileExtension);
        File file = new File(fileName);
        File resizedFile = new File(fileName + "_resized");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            imageResizeService.resizeImage(fileExtension, file, resizedFile);
            s3.putObject(awsS3Properties.getBucket(), fileName, resizedFile);
        } catch (AmazonServiceException | IOException e) {
            e.printStackTrace();
            throw new FileSaveFailException(e.getCause());
        } finally {
            file.delete();
            resizedFile.delete();
        }
        return String.join("/", awsS3Properties.getUrl(), fileName);
    }

    private String validateFileExtension(String filename) {
        String fileExtension = FilenameUtils.getExtension(filename);
        if (fileExtension == null || !VALID_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new InvalidExtensionException();
        }
        return fileExtension;
    }

    private void validateNullFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException();
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.split(awsS3Properties.getUrl() + "/")[1];
        try {
            s3.deleteObject(awsS3Properties.getBucket(), fileName);
        } catch (AmazonServiceException e) {
            throw new FileDeleteFailException();
        }
    }
}
