package com.wootecobook.turkey.friend.controller.api;

import com.wootecobook.turkey.commons.BaseControllerTests;
import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.friend.service.FriendAskService;
import com.wootecobook.turkey.friend.service.dto.FriendCreate;
import com.wootecobook.turkey.friend.service.dto.FriendAskCreate;
import com.wootecobook.turkey.friend.service.dto.FriendAskResponse;
import com.wootecobook.turkey.friend.service.dto.FriendResponse;
import com.wootecobook.turkey.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FriendApiControllerTests extends BaseControllerTests {

    private static final String FRIEND_ASK_API_URI = "/api/friends/asks";
    private static final String FRIEND_API_URI = "/api/friends";
    private static final String SENDER_NAME = "sender";
    private static final String SENDER_EMAIL = "sender@abc.abc";
    private static final String RECEIVER_NAME = "receiver";
    private static final String RECEIVER_EMAIL = "receiver@abc.abc";

    private Long senderId;
    private Long receiverId;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        senderId = addUser(SENDER_NAME, SENDER_EMAIL, VALID_USER_PASSWORD);
        receiverId = addUser(RECEIVER_NAME, RECEIVER_EMAIL, VALID_USER_PASSWORD);
    }

    @Test
    void 친구_요청_테스트() {
        // given
        final FriendAskCreate friendAskCreate = new FriendAskCreate(receiverId);

        // when & then
        webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void 친구_요청_받는_유저_ID가_없는_경우() {
        // given
        final FriendAskCreate friendAskCreate = new FriendAskCreate(Long.MAX_VALUE);

        // when
        ErrorMessage errorMessage = webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(UserService.NOT_FOUND_MESSAGE).isEqualTo(errorMessage.getMessage());
    }

    @Test
    void 친구_요청_조회() {
        // given
        final FriendAskCreate friendAskCreate = new FriendAskCreate(receiverId);

        webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectStatus().isCreated();

        // when & then
        List<FriendAskResponse> friendAskRespons = webTestClient.get().uri(FRIEND_ASK_API_URI)
                .cookie(JSESSIONID, logIn(RECEIVER_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FriendAskResponse.class)
                .returnResult()
                .getResponseBody();

        FriendAskResponse sender = friendAskRespons.get(0);
        assertThat(friendAskRespons.size()).isEqualTo(1);
        assertThat(sender.getSenderName()).isEqualTo(SENDER_NAME);
        assertThat(sender.getSenderId()).isEqualTo(senderId);
    }

    @Test
    void 이미_친구_요청을_보낸_경우() {
        // given
        final FriendAskCreate friendAskCreate = new FriendAskCreate(receiverId);

        webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectStatus().isCreated();

        //when
        ErrorMessage errorMessage = webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MEDIA_TYPE)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(errorMessage.getMessage()).isEqualTo(FriendAskService.ALREADY_FRIEND_REQUEST_EXIST_MESSAGE);
    }

    @Test
    void 친구_요청_수락_후_친구_목록에_등록됐는지_확인() {
        // given
        final FriendAskCreate friendAskCreate = new FriendAskCreate(receiverId);
        FriendAskResponse response = webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FriendAskResponse.class)
                .returnResult()
                .getResponseBody();
        FriendCreate friendCreate = FriendCreate.builder().friendAskId(response.getFriendAskId()).build();

        // when & then
        webTestClient.post().uri(FRIEND_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(RECEIVER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendCreate), FriendCreate.class)
                .exchange()
                .expectStatus().isCreated();

        List<FriendResponse> friendResponses = webTestClient.get().uri(FRIEND_API_URI)
                .cookie(JSESSIONID, logIn(RECEIVER_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FriendResponse.class)
                .returnResult()
                .getResponseBody();

        boolean isFriend = friendResponses.stream().anyMatch(friendResponse -> friendResponse.getRelatedUserId().equals(senderId));
        assertThat(isFriend).isTrue();
    }

    @AfterEach
    void tearDown() {
        deleteUser(senderId, SENDER_EMAIL, VALID_USER_PASSWORD);
        deleteUser(receiverId, RECEIVER_EMAIL, VALID_USER_PASSWORD);
    }
}