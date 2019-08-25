package com.wootecobook.turkey.post.service.dto;

import com.wootecobook.turkey.commons.GoodResponse;
import com.wootecobook.turkey.file.service.FileResponse;
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
    private UserResponse receiver;
    private Contents contents;
    private List<FileResponse> files;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer totalComment;
    private GoodResponse goodResponse;

    @Builder
    public PostResponse(final Long id, final UserResponse author, final UserResponse receiver, final Contents contents,
                        final List<FileResponse> fileResponses, final LocalDateTime createdAt, final LocalDateTime updatedAt,
                        final Integer totalComment, final GoodResponse goodResponse) {
        this.id = id;
        this.author = author;
        this.receiver = receiver;
        this.contents = contents;
        this.files = fileResponses;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.totalComment = totalComment;
        this.goodResponse = goodResponse;
    }

    public static PostResponse from(final Post post, final GoodResponse goodResponse, final int totalComment) {
        List<FileResponse> fileResponses = post.getUploadFiles().stream()
                .map(FileResponse::from)
                .collect(Collectors.toList());

        UserResponse receiverResponse = post.getReceiver()
                .map(UserResponse::from)
                .orElse(null);

        return PostResponse.builder()
                .id(post.getId())
                .author(UserResponse.from(post.getAuthor()))
                .receiver(receiverResponse)
                .contents(post.getContents())
                .fileResponses(fileResponses)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .goodResponse(goodResponse)
                .totalComment(totalComment)
                .build();
    }
}
