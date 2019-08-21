package com.wootecobook.turkey.friend.service;

import com.wootecobook.turkey.friend.domain.Friend;
import com.wootecobook.turkey.friend.domain.FriendRepository;
import com.wootecobook.turkey.friend.domain.FriendAskRepository;
import com.wootecobook.turkey.friend.service.dto.FriendCreate;
import com.wootecobook.turkey.friend.service.dto.FriendAskCreate;
import com.wootecobook.turkey.user.domain.UserRepository;
import com.wootecobook.turkey.user.service.UserService;
import com.wootecobook.turkey.user.service.dto.UserRequest;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FriendServiceTest {

    private static final String SENDER_NAME = "sender";
    private static final String SENDER_EMAIL = "sender@abc.abc";
    private static final String RECEIVER_NAME = "receiver";
    private static final String RECEIVER_EMAIL = "receiver@abc.abc";
    private static final String VALID_PASSWORD = "P@ssw0rd";

    private FriendService friendService;
    private UserService userService;
    private FriendAskService friendAskService;

    private Long senderId;
    private Long receiverId;
    private Long friendAskId;

    @Autowired
    public FriendServiceTest(FriendRepository friendRepository, UserRepository userRepository,
                             FriendAskRepository friendAskRepository) {
        userService = new UserService(userRepository);
        friendAskService = new FriendAskService(friendAskRepository, userService);
        friendService = new FriendService(friendAskService, userService, friendRepository);
    }

    @BeforeEach
    void setUp() {
        UserRequest senderRequest = UserRequest.builder()
                .email(SENDER_EMAIL)
                .name(SENDER_NAME)
                .password(VALID_PASSWORD)
                .build();

        UserRequest receiverRequest = UserRequest.builder()
                .email(RECEIVER_EMAIL)
                .name(RECEIVER_NAME)
                .password(VALID_PASSWORD)
                .build();

        UserResponse senderResponse = userService.save(senderRequest);
        UserResponse receiverResponse = userService.save(receiverRequest);

        senderId = senderResponse.getId();
        receiverId = receiverResponse.getId();

        FriendAskCreate friendAskCreate = FriendAskCreate.builder().receiverId(receiverId).build();
        friendAskId = friendAskService.save(senderId, friendAskCreate).getFriendAskId();
    }

    @Test
    void 친구_요청_수락() {
        FriendCreate friendCreate = FriendCreate.builder()
                .friendAskId(friendAskId)
                .build();

        List<Friend> friends = friendService.save(friendCreate);

        assertThat(friends.size()).isEqualTo(2);

        Friend friend = friends.get(0);
        Friend reverseFriend = friends.get(1);
        assertThat(friend.getRelatingUserId()).isEqualTo(senderId);
        assertThat(friend.getRelatedUserId()).isEqualTo(receiverId);
        assertThat(reverseFriend.getRelatingUserId()).isEqualTo(receiverId);
        assertThat(reverseFriend.getRelatedUserId()).isEqualTo(senderId);
    }
}
