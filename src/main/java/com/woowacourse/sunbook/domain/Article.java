package com.woowacourse.sunbook.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id")
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private ArticleFeature articleFeature;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public Article(String contents, String imageUrl, String videoUrl) {
        articleFeature = new ArticleFeature(contents, imageUrl, videoUrl);
    }

    public void update(ArticleFeature updatedArticleFeature) {
        articleFeature = updatedArticleFeature;
    }
}
