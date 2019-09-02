package techcourse.w3.woostagram.comment.exception;

import techcourse.w3.woostagram.common.exception.WoostagramException;

public class CommentNotFoundException extends WoostagramException {
    private static final String ERROR_COMMENT_NOT_FOUND = "댓글을 찾을 수 없습니다.";

    public CommentNotFoundException() {
        super(ERROR_COMMENT_NOT_FOUND);
    }
}
