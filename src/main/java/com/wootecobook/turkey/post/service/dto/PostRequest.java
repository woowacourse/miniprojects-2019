package com.wootecobook.turkey.post.service.dto;

import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.user.domain.User;

public class PostRequest {
    private String contents;

    public PostRequest(final String contents) {
        this.contents = contents;
    }

    public Post toEntity(final User user) {
        return new Post(new Contents(contents), user);
    }
}
