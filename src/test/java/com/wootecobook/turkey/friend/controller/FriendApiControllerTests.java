package com.wootecobook.turkey.friend.controller;

import com.wootecobook.turkey.BaseControllerTests;
import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.friend.service.FriendAskService;
import com.wootecobook.turkey.friend.service.dto.FriendAskCreate;
import com.wootecobook.turkey.friend.service.dto.FriendAskResponse;
import com.wootecobook.turkey.friend.service.dto.FriendCreate;
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

import static com.wootecobook.turkey.friend.service.FriendService.ALREADY_FRIEND_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FriendApiControllerTests extends BaseControllerTests {

    private static final String FRIEND_ASK_API_URI = "/api/friends/asks";
    private static final String FRIEND_API_URI = "/api/friends";
    private static final String SENDER_NAME = "sender";
    private static final String SENDER_EMAIL = "sender@abc.abc";
    private static final String RECEIVER_NAME = "receiver";
    private static final String RECEIVER_EMAIL = "receiver@abc.abc";
    private static final String MISMATCH_NAME = "mismatch";
    private static final String MISMATCH_EMAIL = "mismatch@abc.abc";

    private Long senderId;
    private Long receiverId;

    @Autowired
    private WebTestClient webTestClient;

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
    void 이미_친구인_경우_친구_요청_실패() {
        // given
        Long friendAskId = createFriendAsk();
        FriendCreate friendCreate = FriendCreate.builder()
                .friendAskId(friendAskId)
                .build();
        createFriend(friendCreate);
        FriendAskCreate friendAskCreate = new FriendAskCreate(receiverId);

        // when & then
        ErrorMessage errorMessage = webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorMessage.getMessage()).isEqualTo(ALREADY_FRIEND_MESSAGE);
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
        createFriendAsk();

        // when & then
        List<FriendAskResponse> friendAskResponse = webTestClient.get().uri(FRIEND_ASK_API_URI)
                .cookie(JSESSIONID, logIn(RECEIVER_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FriendAskResponse.class)
                .returnResult()
                .getResponseBody();

        FriendAskResponse sender = friendAskResponse.get(0);
        assertThat(friendAskResponse.size()).isEqualTo(1);
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
        assertThat(errorMessage.getMessage()).isEqualTo(FriendAskService.ALREADY_FRIEND_ASK_EXIST_MESSAGE);
    }

    @Test
    void 상대방이_친구_요청을_보낸_경우() {
        // given
        final FriendAskCreate friendAskCreate = new FriendAskCreate(receiverId);
        final FriendAskCreate friendAskCreateReverse = new FriendAskCreate(senderId);

        webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectStatus().isCreated();

        //when
        ErrorMessage errorMessage = webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(RECEIVER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreateReverse), FriendAskCreate.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MEDIA_TYPE)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(errorMessage.getMessage()).isEqualTo(FriendAskService.ALREADY_OTHER_FRIEND_ASK_EXIST_MESSAGE);
    }

    @Test
    void 친구_요청_수락_후_친구_목록에_등록됐는지_확인() {
        // given
        Long friendAskId = createFriendAsk();
        FriendCreate friendCreate = FriendCreate.builder().friendAskId(friendAskId).build();

        // when
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

        //then
        assertThat(isFriend).isTrue();
    }

    @Test
    void sender_친구_요청_삭제() {
        //given
        Long friendAskId = createFriendAsk();

        //when & then
        webTestClient.delete()
                .uri(FRIEND_ASK_API_URI + "/" + friendAskId)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void receiver_친구_요청_삭제() {
        //given
        Long friendAskId = createFriendAsk();

        //when & then
        webTestClient.delete()
                .uri(FRIEND_ASK_API_URI + "/" + friendAskId)
                .cookie(JSESSIONID, logIn(RECEIVER_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void 잘못된_유저_친구_요청_삭제() {
        //given
        Long friendAskId = createFriendAsk();
        addUser(MISMATCH_NAME, MISMATCH_EMAIL, VALID_USER_PASSWORD);

        //when
        ErrorMessage errorMessage = webTestClient.delete()
                .uri(FRIEND_ASK_API_URI + "/" + friendAskId)
                .cookie(JSESSIONID, logIn(MISMATCH_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(errorMessage.getMessage()).isEqualTo(FriendAskService.MISMATCHED_USER_MESSAGE);
    }

    @Test
    void 친구_삭제() {
        // given
        Long friendAskId = createFriendAsk();
        FriendCreate friendCreate = FriendCreate.builder()
                .friendAskId(friendAskId)
                .build();

        Long friendId = createFriend(friendCreate);

        //when & then
        webTestClient.delete().uri(FRIEND_API_URI + "/" + friendId)
                .cookie(JSESSIONID, logIn(RECEIVER_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectStatus().isNoContent();
    }

    private Long createFriendAsk() {
        final FriendAskCreate friendAskCreate = new FriendAskCreate(receiverId);
        FriendAskResponse response = webTestClient.post()
                .uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, logIn(SENDER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FriendAskResponse.class)
                .returnResult()
                .getResponseBody();
        return response.getFriendAskId();
    }

    private Long createFriend(FriendCreate friendCreate) {
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

        return friendResponses.stream()
                .filter(friendResponse -> friendResponse.getRelatedUserId().equals(senderId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .getFriendId();
    }

    @AfterEach
    void tearDown() {
        deleteUser(senderId, SENDER_EMAIL, VALID_USER_PASSWORD);
        deleteUser(receiverId, RECEIVER_EMAIL, VALID_USER_PASSWORD);
    }
}