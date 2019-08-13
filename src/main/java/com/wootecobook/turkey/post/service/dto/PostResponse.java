package com.wootecobook.turkey.post.service.dto;

import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import lombok.Getter;

@Getter
public class PostResponse {

    private Contents contents;

    public PostResponse(final Contents contents) {
        this.contents = contents;
    }

    public static PostResponse from(final Post post) {
        Contents contents = post.getContents();

        return new PostResponse(contents);
    }

}
