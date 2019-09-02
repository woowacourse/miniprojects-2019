package techcourse.w3.woostagram.article.exception;

import techcourse.w3.woostagram.common.exception.WoostagramExeception;

public class FileSaveFailException extends WoostagramExeception {
    private static final String ERROR_FILE_UPLOAD = "파일을 서버에 업로드하는데 실패했습니다.";

    public FileSaveFailException(Throwable cause) {
        super(ERROR_FILE_UPLOAD, cause);
    }

    public FileSaveFailException() {
        super(ERROR_FILE_UPLOAD);
    }
}
