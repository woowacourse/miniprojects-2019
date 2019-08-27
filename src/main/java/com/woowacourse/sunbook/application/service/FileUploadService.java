package com.woowacourse.sunbook.application.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.woowacourse.sunbook.application.exception.FileConvertException;
import com.woowacourse.sunbook.application.exception.FileSaveException;
import com.woowacourse.sunbook.domain.fileurl.FileInfo;
import com.woowacourse.sunbook.domain.fileurl.FileNamingStrategy;
import com.woowacourse.sunbook.domain.fileurl.FileUrl;
import com.woowacourse.sunbook.domain.fileurl.RandomNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class FileUploadService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private static final FileNamingStrategy fileNamingStrategy = new RandomNamingStrategy();

    public FileUploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public FileUrl upload(MultipartFile multipartFile, String dirName) {
        File uploadFile = convert(multipartFile)
                .orElseThrow(FileConvertException::new);

        return upload(uploadFile, dirName);
    }

    private FileUrl upload(File uploadFile, String dirName) {
        FileInfo fileInfo = new FileInfo(uploadFile, dirName, fileNamingStrategy);

        String uploadImageUrl = putS3(uploadFile, fileInfo.getFileName() + "." + fileInfo.getExtension());
        removeNewFile(uploadFile);

        return new FileUrl(uploadImageUrl);
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");

            return;
        }
        log.info("파일이 삭제되지 못했습니다.");
    }

    private Optional<File> convert(MultipartFile file) {
        File convertFile = new File(file.getOriginalFilename());
        try {
            if (convertFile.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(convertFile);
                fos.write(file.getBytes());
                return Optional.of(convertFile);
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new FileSaveException();
        }
    }
}