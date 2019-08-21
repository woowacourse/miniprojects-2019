package com.wootecobook.turkey.friend.service;

import com.wootecobook.turkey.friend.domain.Friend;
import com.wootecobook.turkey.friend.domain.FriendAsk;
import com.wootecobook.turkey.friend.domain.FriendRepository;
import com.wootecobook.turkey.friend.service.dto.FriendCreate;
import com.wootecobook.turkey.friend.service.dto.FriendResponse;
import com.wootecobook.turkey.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FriendService {

    private FriendAskService friendAskService;
    private UserService userService;
    private FriendRepository friendRepository;

    public FriendService(FriendAskService friendAskService, UserService userService,
                         FriendRepository friendRepository) {
        this.friendAskService = friendAskService;
        this.userService = userService;
        this.friendRepository = friendRepository;
    }

    public List<Friend> save(FriendCreate friendCreate) {
        FriendAsk friendAsk = friendAskService.findById(friendCreate.getFriendAskId());
        friendAskService.delete(friendAsk);
        return friendRepository.saveAll(friendAsk.createBidirectionalFriends());
    }

    public List<FriendResponse> findAllFriendResponseByRelatingUserId(Long id) {
        return friendRepository.findAllByRelatingUserId(id).stream()
                .map(friend -> FriendResponse.from(friend, userService.findById(friend.getRelatedUserId())))
                .collect(Collectors.toList());
    }
}
