package com.woowacourse.sunbook.domain.fileurl;

import java.io.File;
import java.util.UUID;

public class RandomNamingStrategy implements FileNamingStrategy {

    @Override
    public String getFileName(File file, String dirName) {
        UUID uuid = UUID.randomUUID();

        return dirName + "/" + uuid + "_" + getFileName(file);
    }

    private String getFileName(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        }

        return "";
    }

    @Override
    public String getExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }

        return "";
    }
}