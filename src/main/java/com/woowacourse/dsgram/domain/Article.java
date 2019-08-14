package com.woowacourse.dsgram.domain;


import javax.persistence.*;

@Entity
public class Article {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    public Article() {
    }

    public Article(String contents, String fileName, String filePath) {
        this.contents = contents;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getContents() {
        return contents;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
}
