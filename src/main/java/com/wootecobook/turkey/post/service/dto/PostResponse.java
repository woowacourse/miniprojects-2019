package com.wootecobook.turkey.post.service.dto;

import com.wootecobook.turkey.file.service.FileDto;
import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private UserResponse author;
    private Contents contents;
    private List<FileDto> files;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private PostResponse(Long id, UserResponse author, Contents contents, List<FileDto> fileDtos,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.contents = contents;
        this.files = fileDtos;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostResponse from(final Post post) {
        List<FileDto> fileDtos = post.getFileEntities().stream()
                .map(FileDto::from)
                .collect(Collectors.toList());
        return PostResponse.builder()
                .id(post.getId())
                .author(UserResponse.from(post.getAuthor()))
                .contents(post.getContents())
                .fileDtos(fileDtos)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

}
