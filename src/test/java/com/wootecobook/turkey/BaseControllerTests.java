package com.wootecobook.turkey;

import com.wootecobook.turkey.friend.service.dto.FriendAskCreate;
import com.wootecobook.turkey.friend.service.dto.FriendAskResponse;
import com.wootecobook.turkey.friend.service.dto.FriendCreate;
import com.wootecobook.turkey.login.service.dto.LoginRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import com.wootecobook.turkey.user.service.dto.UserRequest;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class BaseControllerTests {
    protected static final String JSESSIONID = "JSESSIONID";
    protected static final String VALID_USER_PASSWORD = "P@ssw0rd";
    protected static final String VALID_USER_EMAIL = "email@gmail.com";
    protected static final String VALID_USER_NAME = "name";
    protected static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8;

    private static final String USER_API_URI = "/api/users";
    private static final String USER_API_URI_WITH_SLASH = USER_API_URI + "/";
    private static final String POST_URL = "/api/posts";
    private static final String FRIEND_ASK_API_URI = "/api/friends/asks";
    private static final String FRIEND_API_URI = "/api/friends";

    @Autowired
    private WebTestClient webTestClient;

    protected Long addUser(String name, String email, String password) {
        UserRequest userRequest = UserRequest.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        UserResponse result = webTestClient.post()
                .uri(USER_API_URI)
                .contentType(MEDIA_TYPE)
                .accept(MEDIA_TYPE)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectBody(UserResponse.class)
                .returnResult()
                .getResponseBody();

        return result.getId();
    }

    protected void deleteUser(Long id, String email, String password) {
        webTestClient.delete()
                .uri(USER_API_URI_WITH_SLASH + id)
                .cookie(JSESSIONID, logIn(email, password))
                .exchange();
    }

    protected String logIn(String email, String password) {
        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        return webTestClient.post().uri("/login")
                .contentType(MEDIA_TYPE)
                .accept(MEDIA_TYPE)
                .body(Mono.just(loginRequest), LoginRequest.class)
                .exchange()
                .returnResult(String.class)
                .getResponseCookies().get(JSESSIONID).get(0).getValue();
    }

    protected Long addPost(String jSessionId, String contents, List<ByteArrayResource> files, List<Long> taggedUsers) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        for (ByteArrayResource file : files) {
            bodyBuilder.part("files", file, MediaType.parseMediaType("image/jpeg"));
        }
        bodyBuilder.part("contents", contents);
        for (Long taggedUserId : taggedUsers) {
            bodyBuilder.part("taggedUsers", taggedUserId);
        }

        PostResponse postResponse = webTestClient.post().uri(POST_URL)
                .cookie(JSESSIONID, jSessionId)
                .syncBody(bodyBuilder.build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();

        return postResponse.getId();
    }

    protected Long askFriend(String jSessionId, Long receiverId) {
        final FriendAskCreate friendAskCreate = new FriendAskCreate(receiverId);

        FriendAskResponse response = webTestClient.post().uri(FRIEND_ASK_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, jSessionId)
                .body(Mono.just(friendAskCreate), FriendAskCreate.class)
                .exchange()
                .expectBody(FriendAskResponse.class)
                .returnResult()
                .getResponseBody();

        return response.getFriendAskId();
    }

    protected void makeFriend(String receiverJSessionId, Long friendAskId) {
        FriendCreate friendCreate = FriendCreate.builder().friendAskId(friendAskId).build();

        webTestClient.post().uri(FRIEND_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, receiverJSessionId)
                .body(Mono.just(friendCreate), FriendCreate.class)
                .exchange()
                .expectStatus().isCreated();
    }

    protected void makeFriend(String senderJSessionId, String receiverJSessionId, Long receiverId) {
        Long friendAskId = askFriend(senderJSessionId, receiverId);
        FriendCreate friendCreate = FriendCreate.builder().friendAskId(friendAskId).build();

        webTestClient.post().uri(FRIEND_API_URI)
                .contentType(MEDIA_TYPE)
                .cookie(JSESSIONID, receiverJSessionId)
                .body(Mono.just(friendCreate), FriendCreate.class)
                .exchange()
                .expectStatus().isCreated();
    }

}
