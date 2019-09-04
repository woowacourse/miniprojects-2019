package com.wootecobook.turkey.user.service;

import com.wootecobook.turkey.friend.service.FriendAskService;
import com.wootecobook.turkey.friend.service.FriendService;
import com.wootecobook.turkey.user.service.exception.UserDeleteException;
import com.wootecobook.turkey.user.service.exception.UserMismatchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDeleteService {

    private final UserService userService;
    private final FriendAskService friendAskService;
    private final FriendService friendService;
    private final IntroductionService introductionService;

    public UserDeleteService(final UserService userService,
                             final FriendAskService friendAskService,
                             final FriendService friendService,
                             final IntroductionService introductionService) {
        this.userService = userService;
        this.friendAskService = friendAskService;
        this.friendService = friendService;
        this.introductionService = introductionService;
    }

    public void delete(final Long userId, final Long sessionUserId) {
        matchId(userId, sessionUserId);
        try {
            userService.delete(userId, sessionUserId);
            friendAskService.deleteBySenderIdOrReceiverId(userId);
            friendService.deleteByRelatedUserIdOrRelatingUserId(userId);
            introductionService.delete(userId);
        } catch (Exception e) {
            throw new UserDeleteException();
        }
    }

    private void matchId(final Long userId, final Long sessionUserId) {
        if (userId == null || !userId.equals(sessionUserId)) {
            throw new UserMismatchException();
        }
    }
}
