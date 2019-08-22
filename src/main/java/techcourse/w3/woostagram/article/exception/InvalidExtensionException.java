package techcourse.w3.woostagram.article.exception;

public class InvalidExtensionException extends RuntimeException {
    private static final String ERROR_FILE_EXTENSION_NOT_SUPPROT = "지원하지 않는 파일 확장자입니다.";

    public InvalidExtensionException() {
        super(ERROR_FILE_EXTENSION_NOT_SUPPROT);
    }
}
