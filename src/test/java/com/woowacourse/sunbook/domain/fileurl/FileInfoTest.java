package com.woowacourse.sunbook.domain.fileurl;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class FileInfoTest {
    private static final String FILE_NAME = "testFile";
    private static final String FILE_EXTENSION = "jpg";
    private static final File file = new File(FILE_NAME + "." + FILE_EXTENSION);
    private static final String DIR_NAME = "/test";

    @Test
    void 파일_이름_검사() {
        FileNamingStrategy fileNamingStrategy = new LocalNamingStrategy();

        FileInfo fileInfo = new FileInfo(file, DIR_NAME, fileNamingStrategy);
        assertThat(fileInfo.getFileName()).isEqualTo(DIR_NAME + "/" + FILE_NAME);
    }

    @Test
    void 파일_확장자_검사() {
        FileNamingStrategy fileNamingStrategy = new LocalNamingStrategy();

        FileInfo fileInfo = new FileInfo(file, DIR_NAME, fileNamingStrategy);
        assertThat(fileInfo.getExtension()).isEqualTo(FILE_EXTENSION);
    }

}