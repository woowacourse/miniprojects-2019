package com.wootecobook.turkey.file.service;

import com.wootecobook.turkey.file.domain.FileFeature;
import com.wootecobook.turkey.file.domain.UploadFile;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FileDto {

    private Long id;
    private FileFeature fileFeature;
    private UserResponse owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public FileDto(Long id, FileFeature fileFeature, UserResponse owner, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fileFeature = fileFeature;
        this.owner = owner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FileDto from(UploadFile uploadFile) {
        return FileDto.builder()
                .id(uploadFile.getId())
                .fileFeature(uploadFile.getFileFeature())
                .owner(UserResponse.from(uploadFile.getOwner()))
                .createdAt(uploadFile.getCreatedAt())
                .updatedAt(uploadFile.getUpdatedAt())
                .build();
    }
}
