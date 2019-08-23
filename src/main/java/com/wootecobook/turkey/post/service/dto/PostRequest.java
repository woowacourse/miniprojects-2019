package com.wootecobook.turkey.post.service.dto;

import com.wootecobook.turkey.file.domain.UploadFile;
import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {

    @NotBlank
    private String contents;
    private List<MultipartFile> files;

    @Builder
    private PostRequest(final String contents, final List<MultipartFile> files) {
        this.contents = contents;
        this.files = files;
    }

    public Post toEntity(final User author, final List<UploadFile> savedFiles) {
        return Post.builder()
                .author(author)
                .contents(new Contents(contents))
                .uploadFiles(savedFiles)
                .build();
    }
}
