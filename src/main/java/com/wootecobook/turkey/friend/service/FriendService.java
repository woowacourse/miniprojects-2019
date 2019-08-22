package com.wootecobook.turkey.friend.service;

import com.wootecobook.turkey.friend.domain.Friend;
import com.wootecobook.turkey.friend.domain.FriendAsk;
import com.wootecobook.turkey.friend.domain.FriendRepository;
import com.wootecobook.turkey.friend.service.dto.FriendCreate;
import com.wootecobook.turkey.friend.service.dto.FriendResponse;
import com.wootecobook.turkey.friend.service.exception.AlreadyFriendException;
import com.wootecobook.turkey.friend.service.exception.MismatchedUserException;
import com.wootecobook.turkey.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FriendService {

    public static final String NOT_FOUND_FRIEND_MESSAGE = "친구를 찾을 수 없습니다.";
    public static final String MISMATCHED_USER_MESSAGE = "유저가 일치하지 않습니다.";
    public static final String ALREADY_FRIEND_MESSAGE = "이미 친구입니다.";

    private FriendAskService friendAskService;
    private UserService userService;
    private FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository, FriendAskService friendAskService,
                         UserService userService) {
        this.friendRepository = friendRepository;
        this.friendAskService = friendAskService;
        this.userService = userService;
    }

    public List<Friend> save(FriendCreate friendCreate) {
        FriendAsk friendAsk = friendAskService.findById(friendCreate.getFriendAskId());

        checkAlreadyFriend(friendAsk.getSenderId(), friendAsk.getReceiverId());

        friendAskService.delete(friendAsk);
        return friendRepository.saveAll(friendAsk.createBidirectionalFriends());
    }

    @Transactional(readOnly = true)
    public List<FriendResponse> findAllFriendResponseByRelatingUserId(Long id) {
        return friendRepository.findAllByRelatingUserId(id).stream()
                .map(friend -> FriendResponse.from(friend, userService.findById(friend.getRelatedUserId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Friend findById(Long id) {
        return friendRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_FRIEND_MESSAGE));
    }

    public void deleteById(Long friendId, Long userSessionId) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_FRIEND_MESSAGE));
        Friend reverseFriend = friendRepository
                .findByRelatingUserIdAndRelatedUserId(friend.getRelatedUserId(), friend.getRelatingUserId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_FRIEND_MESSAGE));

        checkUserId(userSessionId, friend, reverseFriend);

        friendRepository.delete(friend);
        friendRepository.delete(reverseFriend);
    }

    private void checkUserId(Long userSessionId, Friend friend, Friend reverseFriend) {
        if (!(friend.matchRelatingUserId(userSessionId) && reverseFriend.matchRelatedUserId(userSessionId))) {
            throw new MismatchedUserException(MISMATCHED_USER_MESSAGE);
        }
    }

    public void checkAlreadyFriend(Long relatingId, Long relatedId) {
        if (friendRepository.findByRelatingUserIdAndRelatedUserId(relatingId, relatedId).isPresent()) {
            throw new AlreadyFriendException(ALREADY_FRIEND_MESSAGE);
        }
    }

    public void deleteByRelatedUserIdOrRelatingUserId(Long userId) {
        friendRepository.deleteByRelatedUserIdOrRelatingUserId(userId, userId);
    }
}
