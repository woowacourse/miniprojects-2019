package com.woowacourse.edd.application.response;

import com.woowacourse.edd.domain.User;

public class CommentResponse {

    private Long id;
    private String contents;
    private Author author;
    private String createDate;

    public CommentResponse(Long id, String contents, User author, String createDate) {
        this.id = id;
        this.contents = contents;
        this.author = new Author(author.getId(), author.getName());
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public Author getAuthor() {
        return author;
    }

    public String getCreateDate() {
        return createDate;
    }

    class Author {
        private Long id;
        private String name;

        public Author(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
