package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.service.exception.FileUploadException;
import com.woowacourse.dsgram.service.vo.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    @Value("${ARTICLE_UPLOAD_PATH}")
    private String ARTICLE_UPLOAD_PATH;

    @Value("${TOMCAT_PATH}")
    private String TOMCAT_PATH;

    @Value("${BASE_DIR}")
    private String BASE_DIR;

    public FileInfo save(MultipartFile multiFile) {
        String fileName = UUID.randomUUID().toString() + multiFile.getOriginalFilename();
        String filePath = BASE_DIR + TOMCAT_PATH + ARTICLE_UPLOAD_PATH;

        makeDirectory(filePath);
        saveFile(multiFile, fileName);

        return new FileInfo(fileName, filePath);
    }

    private void makeDirectory(String filePath) {
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void saveFile(MultipartFile multiFile, String fileName) {
        // Mac에선 full-path 입력해야함
        File file = new File(ARTICLE_UPLOAD_PATH, fileName);
        try {
            multiFile.transferTo(file);
        } catch (IOException e) {
            throw new FileUploadException("파일저장경로를 찾지못했습니다.", e);
        }
    }
}
