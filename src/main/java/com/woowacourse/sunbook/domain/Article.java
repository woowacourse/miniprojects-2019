package com.woowacourse.sunbook.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private ArticleFeature articleFeature;

    public Article(String contents, String imageUrl, String videoUrl) {
        articleFeature = new ArticleFeature(contents, imageUrl, videoUrl);
    }
}
