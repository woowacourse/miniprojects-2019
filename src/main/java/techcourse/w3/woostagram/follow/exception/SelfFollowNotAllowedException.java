package techcourse.w3.woostagram.follow.exception;

import techcourse.w3.woostagram.common.exception.WoostagramExeception;

public class SelfFollowNotAllowedException extends WoostagramExeception {
    public SelfFollowNotAllowedException() {
        super("자기 자신을 팔로우할 수 없습니다.");
    }
}
