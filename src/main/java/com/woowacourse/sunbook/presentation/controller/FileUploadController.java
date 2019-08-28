package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.service.FileUploadService;
import com.woowacourse.sunbook.domain.fileurl.FileUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(final FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<FileUrl> upload(@RequestParam("data") MultipartFile multipartFile) {
        return ResponseEntity.ok().body(fileUploadService.upload(multipartFile, "sunbook"));
    }
}
