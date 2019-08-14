package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.ArticleRequest;
import com.woowacourse.dsgram.service.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class ArticleController {

    @GetMapping("/articles/writing")
    public String moveToWritePage() {
        return "article-edit";
    }

    private static final String UPLOAD_FOLDER_PATH = "/articles/files";
    private static final String TOMCAT_PATH = "/work/Tomcat/localhost/ROOT";

    @Value("${basedir}")
    private String basedir;

    @GetMapping("/")
    public String foo() {
        return "article-edit";
    }

    @PostMapping("/articles")
    public String saveFile(@RequestParam("file") MultipartFile multiFile) {
        String fileName = multiFile.getOriginalFilename();
        String uploadPath = basedir + TOMCAT_PATH + UPLOAD_FOLDER_PATH;
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(UPLOAD_FOLDER_PATH, fileName);
        try {
            multiFile.transferTo(file);
        } catch (IOException e) {
            throw new FileUploadException("파일저장경로를 찾지못했습니다.", e);
        }
        return "article-edit";
    }
}
