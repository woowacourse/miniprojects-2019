package com.woowacourse.sunbook.domain.fileurl;

import lombok.Getter;

import java.io.File;

@Getter
public class FileInfo {
    private String fileName;
    private String extension;

    public FileInfo(final File uploadFile, final String dirName, final FileNamingStrategy fileNamingStrategy) {
        this.fileName = fileNamingStrategy.getFileName(uploadFile, dirName);
        this.extension = fileNamingStrategy.getExtension(uploadFile);
    }
}
