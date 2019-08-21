package com.wootecobook.turkey.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class S3Connector {
    private static final Logger log = LoggerFactory.getLogger(S3Connector.class);

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3Connector(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String upload(MultipartFile multipartFile, String filePath) throws IOException {
        File uploadFile = convert(multipartFile);

        return upload(uploadFile, filePath);
    }

    private String upload(File file, String filePath) {
        String uploadImageUrl = putS3(file, filePath);
        removeNewFile(file);
        return uploadImageUrl;
    }

    private void removeNewFile(File file) {
        if (file.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다");
        }
    }

    private String putS3(File file, String filePath) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, filePath, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, filePath).toString();
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(multipartFile.getOriginalFilename());
        if (!convertFile.createNewFile()) {
            throw new IllegalArgumentException("MultipartFile -> File 전환 실패");
        }

        try (FileOutputStream fos = new FileOutputStream((convertFile))) {
            fos.write(multipartFile.getBytes());
        }
        return convertFile;
    }

    public void delete(String filePath) {
        amazonS3Client.deleteObject(bucket, filePath);
    }
}
