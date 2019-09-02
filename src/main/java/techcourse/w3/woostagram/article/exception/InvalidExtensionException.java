package techcourse.w3.woostagram.article.exception;

import techcourse.w3.woostagram.common.exception.WoostagramExeception;

public class InvalidExtensionException extends WoostagramExeception {
    private static final String ERROR_FILE_EXTENSION_NOT_SUPPROT = "지원하지 않는 파일 확장자입니다.";

    public InvalidExtensionException() {
        super(ERROR_FILE_EXTENSION_NOT_SUPPROT);
    }
}
