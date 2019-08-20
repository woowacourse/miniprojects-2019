package techcourse.w3.woostagram.follow.exception;

public class FollowNotFoundException extends RuntimeException {
    public FollowNotFoundException() {
        super("존재하지 않는 팔로우 관계입니다.");
    }
}
