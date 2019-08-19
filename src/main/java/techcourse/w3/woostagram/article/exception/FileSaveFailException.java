package techcourse.w3.woostagram.article.exception;

public class FileSaveFailException extends RuntimeException{
    public FileSaveFailException() {
        super("파일을 서버에 업로드하는 데 실패했습니다.");
    }
}
