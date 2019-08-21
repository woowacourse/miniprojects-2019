package com.woowacourse.dsgram.service;

public class ArticleFileNamingStrategy extends CommonFileNamingStrategy {

    private static final String ARTICLES_FILES_PATH = "/articles/files";

    @Override
    public String makePath() {
        return getBasePath().concat(SEPARATOR)
                .concat(getDate())
                .concat(SEPARATOR)
                .concat(ARTICLES_FILES_PATH);
    }
}
