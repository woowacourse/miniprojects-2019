package techcourse.w3.woostagram.common.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.article.exception.FileSaveFailException;
import techcourse.w3.woostagram.article.exception.InvalidExtensionException;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    private static final String PATH_DELIMITER = ".";
    //@Value("${file.upload.directory}")
//    private static final String UPLOAD_PATH = "/home/yumin/Codes/WoowaTech/Level2/miniprojects-2019/uploads/";
    private static final String UPLOAD_PATH = "/home/ubuntu/miniprojects-2019/uploads/";
    private static final List<String> VALID_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    public String saveMultipartFile(MultipartFile multipartFile) {
        String fileExtension = validateFileExtension(multipartFile);
        String fullPath = getUploadPath(fileExtension);
        File file = new File(fullPath);
        try {
            file.createNewFile();
            multipartFile.transferTo(file);
        } catch (Exception e) {
            file.delete();
            throw new FileSaveFailException();
        }
        return fullPath;
    }

    private String getUploadPath(String fileExtension) {
        return String.join(PATH_DELIMITER, UPLOAD_PATH + UUID.randomUUID().toString(), fileExtension);
    }

    private String validateFileExtension(MultipartFile multipartFile) {
        String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (fileExtension == null || !VALID_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new InvalidExtensionException();
        }
        return fileExtension;
    }
}
