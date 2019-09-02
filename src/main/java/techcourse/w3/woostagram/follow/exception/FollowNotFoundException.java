package techcourse.w3.woostagram.follow.exception;

import techcourse.w3.woostagram.common.exception.WoostagramExeception;

public class FollowNotFoundException extends WoostagramExeception {
    public FollowNotFoundException() {
        super("존재하지 않는 팔로우 관계입니다.");
    }
}
