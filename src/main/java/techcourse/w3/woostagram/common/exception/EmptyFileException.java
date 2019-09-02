package techcourse.w3.woostagram.common.exception;

public class EmptyFileException extends WoostagramException {

    private static final String ERROR_EMPTY_FILE = "사진을 선택해주세요.";

    public EmptyFileException() {
        super(ERROR_EMPTY_FILE);
    }
}
