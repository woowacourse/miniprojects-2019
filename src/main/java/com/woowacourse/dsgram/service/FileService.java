package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.UserProfileImage;
import com.woowacourse.dsgram.service.exception.FileUploadException;
import com.woowacourse.dsgram.service.exception.NotFoundFileException;
import com.woowacourse.dsgram.service.exception.UserDirNullException;
import com.woowacourse.dsgram.service.vo.FileInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    private static String ARTICLE_UPLOAD_PATH = "/articles/files";

    public FileInfo save(MultipartFile multiFile) {
        String fileName = UUID.randomUUID().toString() + multiFile.getOriginalFilename();
        String filePath = getFilePath();

        makeDirectory(filePath);
        saveFile(multiFile, fileName, filePath);

        return new FileInfo(fileName, filePath);
    }

    private String getFilePath() {
        Optional<String> path = Optional.of(System.getProperty("user.dir"));

        return path.orElseThrow(() ->
                new UserDirNullException("java system 변수에 User dir이 존재하지 않습니다."))
                .concat(ARTICLE_UPLOAD_PATH);
    }

    private void makeDirectory(String filePath) {
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void saveFile(MultipartFile multiFile, String fileName, String filePath) {
        File file = new File(filePath, fileName);
        try {
            multiFile.transferTo(file);
        } catch (IOException e) {
            throw new FileUploadException("파일저장경로를 찾지못했습니다.", e);
        }
    }

    public byte[] readFile(Article article) {
        File file = new File(article.getFilePath() + "/" + article.getFileName());

        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encode(bytes);
        } catch (IOException e) {
            throw new NotFoundFileException("파일을 찾지 못했습니다.", e);
        }

    }

    public byte[] readFile(UserProfileImage userProfileImage) {
        File file = new File(userProfileImage.getFilePath() + "/" + userProfileImage.getFileName());

        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encode(bytes);
        } catch (IOException e) {
            throw new NotFoundFileException("파일을 찾지 못했습니다.", e);
        }

    }
}
