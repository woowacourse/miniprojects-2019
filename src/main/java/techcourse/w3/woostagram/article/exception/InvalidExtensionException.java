package techcourse.w3.woostagram.article.exception;

public class InvalidExtensionException extends RuntimeException {
    public InvalidExtensionException() {
        super("지원하지 않는 파일 확장자입니다.");
    }
}
