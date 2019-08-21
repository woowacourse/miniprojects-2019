package com.woowacourse.dsgram.service;

public interface FileNamingStrategy {
    String makeUniquePrefix(String originalFilename);

    String makePath();
}
