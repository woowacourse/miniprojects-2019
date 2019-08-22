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

    private UserService userService;
    private FriendAskService friendAskService;
    private FriendService friendService;

    public UserDeleteService(UserService userService, FriendAskService friendAskService, FriendService friendService) {
        this.userService = userService;
        this.friendAskService = friendAskService;
        this.friendService = friendService;
    }

    public void delete(Long userId, Long sessionUserId) {
        matchId(userId, sessionUserId);
        try {
            userService.delete(userId);
            friendAskService.deleteBySenderIdOrReceiverId(userId);
            friendService.deleteByRelatedUserIdOrRelatingUserId(userId);
        } catch (Exception e) {
            throw new UserDeleteException();
        }
    }

    private void matchId(Long userId, Long sessionUserId) {
        if (userId == null || !userId.equals(sessionUserId)) {
            throw new UserMismatchException();
        }
    }
}
