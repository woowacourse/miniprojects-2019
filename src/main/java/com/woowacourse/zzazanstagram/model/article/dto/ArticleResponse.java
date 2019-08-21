package com.woowacourse.zzazanstagram.model.article.dto;

import com.woowacourse.zzazanstagram.model.comment.dto.CommentResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleResponse {
    private Long id;
    private String image;
    private String contents;
    //    private String hashTag;
    private String nickName;
    private String profileImage;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<CommentResponse> commentResponses;
    //좋아요 추가하기

    public ArticleResponse() {
    }

    private ArticleResponse(Long id, String image, String contents, String nickName, String profileImage, LocalDateTime createdDate, LocalDateTime lastModifiedDate, List<CommentResponse> commentResponses) {
        this.id = id;
        this.image = image;
        this.contents = contents;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.commentResponses = commentResponses;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getContents() {
        return contents;
    }

    public String getNickName() {
        return nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public List<CommentResponse> getCommentResponses() {
        return commentResponses;
    }

    public static final class ArticleResponseBuilder {

        private Long id;
        private String image;
        private String contents;
        //    private String hashTag;
        private String nickName;
        private String profileImage;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private List<CommentResponse> commentResponses;

        private ArticleResponseBuilder() {
        }

        public static ArticleResponseBuilder anArticleResponse() {
            return new ArticleResponseBuilder();
        }

        public ArticleResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ArticleResponseBuilder image(String image) {
            this.image = image;
            return this;
        }

        public ArticleResponseBuilder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public ArticleResponseBuilder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public ArticleResponseBuilder profileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public ArticleResponseBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ArticleResponseBuilder lastModifiedDate(LocalDateTime lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public ArticleResponseBuilder commentResponses(List<CommentResponse> commentResponses) {
            this.commentResponses = commentResponses;
            return this;
        }

        public ArticleResponse build() {
            return new ArticleResponse(id, image, contents, nickName, profileImage, createdDate, lastModifiedDate, commentResponses);
        }
    }
}
