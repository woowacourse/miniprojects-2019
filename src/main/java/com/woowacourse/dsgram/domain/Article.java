package com.woowacourse.dsgram.domain;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Article {
    public static final String REGEX = "#([0-9a-zA-Z가-힣_]{2,30})";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false, length = 240)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "fk_article_to_user"))
    private User author;

    public Article(String contents, String fileName, String filePath, User author) {
        this.contents = contents;
        this.fileName = fileName;
        this.filePath = filePath;
        this.author = author;
    }

    public Article update(String contents) {
        this.contents = contents;
        return this;
    }

    public boolean notEqualAuthorId(long id) {
        return this.author.notEqualId(id);
    }

    public Set<String> getKeyword() {
        Matcher matcher = Pattern.compile(REGEX).matcher(contents);

        Set<String> keywords = new HashSet<>();
        while (matcher.find()) {
            keywords.add(matcher.group());
        }
        return keywords;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", author=" + author +
                '}';
    }
}
