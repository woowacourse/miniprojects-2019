package com.wootecobook.turkey.file.service;

import com.wootecobook.turkey.file.domain.FileFeature;
import com.wootecobook.turkey.file.domain.UploadFile;
import com.wootecobook.turkey.file.domain.UploadFileRepository;
import com.wootecobook.turkey.user.domain.User;
import com.wootecobook.turkey.utils.S3Connector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
public class UploadFileService {

    private final S3Connector s3Connector;
    private final UploadFileRepository uploadFileRepository;

    public UploadFileService(final S3Connector s3Connector, final UploadFileRepository fileFeatureRepository) {
        this.s3Connector = s3Connector;
        this.uploadFileRepository = fileFeatureRepository;
    }

    public UploadFile save(final MultipartFile multipartFile, final String directoryName, final User owner) {
        String uploadPath = getUploadPath(multipartFile, directoryName);

        FileFeature fileFeature = FileFeature.builder()
                .path(uploadPath)
                .originalName(multipartFile.getOriginalFilename())
                .type(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .build();
        UploadFile savedFileFeature = uploadFileRepository.save(new UploadFile(fileFeature, owner));

        return savedFileFeature;
    }

    private String getUploadPath(final MultipartFile multipartFile, final String directoryName) {
        try {
            String fileName = createUniqueFileName();
            return s3Connector.upload(multipartFile, getFilePath(directoryName, fileName));
        } catch (IOException e) {
            throw new FailedSaveFileException(e.getMessage());
        }
    }

    private String getFilePath(final String directoryName, final String fileName) {
        return String.format("%s/%s", directoryName, fileName);
    }

    private String createUniqueFileName() {
        return String.valueOf(UUID.randomUUID());
    }
}
