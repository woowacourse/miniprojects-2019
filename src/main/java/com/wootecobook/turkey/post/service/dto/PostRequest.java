package com.wootecobook.turkey.post.service.dto;

import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;

public class PostRequest {
    private String contents;

    public PostRequest(final String contents) {
        this.contents = contents;
    }

    public Post toEntity() {
        return new Post(new Contents(contents));
    }
}
