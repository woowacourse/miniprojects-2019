package techcourse.w3.woostagram.comment.exception;

public class CommentNotFoundException extends RuntimeException {
    private static final String ERROR_COMMENT_NOT_FOUND = "댓글을 찾을 수 없습니다.";

    public CommentNotFoundException() {
        super(ERROR_COMMENT_NOT_FOUND);
    }
}
