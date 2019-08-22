package techcourse.w3.woostagram.article.exception;

public class FileSaveFailException extends RuntimeException {
    private static final String ERROR_FILE_UPLOAD = "파일을 서버에 업로드하는 데 실패했습니다.";

    public FileSaveFailException() {
        super(ERROR_FILE_UPLOAD);
    }
}
