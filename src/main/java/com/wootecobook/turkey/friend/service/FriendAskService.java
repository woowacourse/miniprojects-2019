package com.wootecobook.turkey.friend.service;

import com.wootecobook.turkey.friend.domain.FriendAsk;
import com.wootecobook.turkey.friend.domain.FriendAskRepository;
import com.wootecobook.turkey.friend.service.dto.FriendAskCreate;
import com.wootecobook.turkey.friend.service.dto.FriendAskResponse;
import com.wootecobook.turkey.friend.service.exception.FriendAskFailException;
import com.wootecobook.turkey.friend.service.exception.MismatchedUserException;
import com.wootecobook.turkey.user.domain.User;
import com.wootecobook.turkey.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FriendAskService {

    public static final String ALREADY_FRIEND_ASK_EXIST_MESSAGE = "이미 친구 요청을 보냈습니다.";
    public static final String ALREADY_OTHER_FRIEND_ASK_EXIST_MESSAGE = "상대방이 친구 요청을 보냈습니다.";
    public static final String NOT_FOUND_FRIEND_ASK_MESSAGE = "친구 요청을 찾을 수 없습니다.";
    public static final String MISMATCHED_USER_MESSAGE = "유저가 일치하지 않습니다.";

    private final FriendAskRepository friendAskRepository;
    private final UserService userService;

    public FriendAskService(final FriendAskRepository friendAskRepository, final UserService userService) {
        this.friendAskRepository = friendAskRepository;
        this.userService = userService;
    }

    public FriendAskResponse save(final Long senderId, final FriendAskCreate friendAskCreate) {
        User sender = userService.findById(senderId);
        User receiver = userService.findById(friendAskCreate.getReceiverId());

        checkFriendAskExist(senderId, friendAskCreate.getReceiverId(), ALREADY_FRIEND_ASK_EXIST_MESSAGE);
        checkFriendAskExist(friendAskCreate.getReceiverId(), senderId, ALREADY_OTHER_FRIEND_ASK_EXIST_MESSAGE);

        FriendAsk friendAsk = friendAskRepository.save(friendAskCreate.toEntity(senderId));
        return FriendAskResponse.from(friendAsk, sender, receiver);
    }

    private void checkFriendAskExist(final Long senderId, final Long receiverId, final String message) {
        if (friendAskRepository.findBySenderIdAndReceiverId(senderId, receiverId).isPresent()) {
            throw new FriendAskFailException(message);
        }
    }

    @Transactional(readOnly = true)
    public List<FriendAskResponse> findAllByReceiverId(final Long id) {
        return friendAskRepository.findAllByReceiverId(id).stream()
                .map(friendAsk -> FriendAskResponse.from(friendAsk,
                        userService.findById(friendAsk.getSenderId()),
                        userService.findById(friendAsk.getReceiverId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FriendAsk findById(final Long friendAskId) {
        return friendAskRepository.findById(friendAskId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_FRIEND_ASK_MESSAGE));
    }

    public void delete(final FriendAsk friendAsk) {
        friendAskRepository.delete(friendAsk);
    }

    public void deleteById(final Long friendAskId, final Long userSessionId) {
        FriendAsk friendAsk = findById(friendAskId);
        checkUserId(friendAsk, userSessionId);
        friendAskRepository.delete(friendAsk);
    }

    private void checkUserId(final FriendAsk friendAsk, final Long userSessionId) {
        if (!friendAsk.matchUserId(userSessionId)) {
            throw new MismatchedUserException(MISMATCHED_USER_MESSAGE);
        }
    }

    public void deleteBySenderIdOrReceiverId(final Long userId) {
        friendAskRepository.deleteBySenderIdOrReceiverId(userId, userId);
    }
}
