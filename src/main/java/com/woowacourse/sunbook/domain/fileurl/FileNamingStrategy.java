package com.woowacourse.sunbook.domain.fileurl;

import java.io.File;

public interface FileNamingStrategy {
    String getFileName(File file, String dirName);
    String getExtension(File file);
}