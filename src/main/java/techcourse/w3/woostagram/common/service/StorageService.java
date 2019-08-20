package techcourse.w3.woostagram.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String saveMultipartFile(MultipartFile multipartFile);

    void deleteFile(String fileUrl);
}
