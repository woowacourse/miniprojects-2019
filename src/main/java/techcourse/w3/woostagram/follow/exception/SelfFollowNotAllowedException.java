package techcourse.w3.woostagram.follow.exception;

import techcourse.w3.woostagram.common.exception.WoostagramException;

public class SelfFollowNotAllowedException extends WoostagramException {
    public SelfFollowNotAllowedException() {
        super("자기 자신을 팔로우할 수 없습니다.");
    }
}
