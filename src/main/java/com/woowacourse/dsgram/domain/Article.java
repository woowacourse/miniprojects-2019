package com.woowacourse.dsgram.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
public class Article {
    public static final String REGEX = "#([0-9a-zA-Z가-힣_]{2,30})";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false, length = 240)
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

    public Set<String> getKeyword() {
        Matcher matcher = Pattern.compile(REGEX).matcher(contents);

        Set<String> keywords = new HashSet<>();
        while (matcher.find()) {
            keywords.add(matcher.group());
        }
        return keywords;
    }
}
