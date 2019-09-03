package techcourse.w3.woostagram.common.exception;

public class InvalidImageSize extends WoostagramException {
    private static final String ERROR_IMAGE_SIZE = "지원하지 않는 이미지 사이즈 입니다.";

    public InvalidImageSize() {
        super(ERROR_IMAGE_SIZE);
    }
}
