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
public class FileResponse {

    private Long id;
    private FileFeature fileFeature;
    private UserResponse owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public FileResponse(final Long id, final FileFeature fileFeature, final UserResponse owner,
                        final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.fileFeature = fileFeature;
        this.owner = owner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FileResponse from(final UploadFile uploadFile) {
        return FileResponse.builder()
                .id(uploadFile.getId())
                .fileFeature(uploadFile.getFileFeature())
                .owner(UserResponse.from(uploadFile.getOwner()))
                .createdAt(uploadFile.getCreatedAt())
                .updatedAt(uploadFile.getUpdatedAt())
                .build();
    }
}
