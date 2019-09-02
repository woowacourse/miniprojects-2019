package techcourse.w3.woostagram.article.exception;

import techcourse.w3.woostagram.common.exception.WoostagramExeception;

public class FileDeleteFailException extends WoostagramExeception {
    private static final String ERROR_FILE_DELETE = "파일을 서버에서 삭제하는데 실패했습니다.";

    public FileDeleteFailException() {
        super(ERROR_FILE_DELETE);
    }
}
