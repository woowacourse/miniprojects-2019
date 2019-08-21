package com.wootecobook.turkey.friend.controller.api;

import com.wootecobook.turkey.commons.resolver.UserSession;
import com.wootecobook.turkey.friend.service.FriendAskService;
import com.wootecobook.turkey.friend.service.FriendService;
import com.wootecobook.turkey.friend.service.dto.FriendCreate;
import com.wootecobook.turkey.friend.service.dto.FriendAskCreate;
import com.wootecobook.turkey.friend.service.dto.FriendAskResponse;
import com.wootecobook.turkey.friend.service.dto.FriendResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendApiController {

    private FriendAskService friendAskService;
    private FriendService friendService;

    public FriendApiController(FriendAskService friendAskService, FriendService friendService) {
        this.friendAskService = friendAskService;
        this.friendService = friendService;
    }

    @GetMapping("/asks")
    public ResponseEntity<List<FriendAskResponse>> showAsk(UserSession userSession) {
        return ResponseEntity.ok(friendAskService.findAllByReceiverId(userSession.getId()));
    }

    @PostMapping("/asks")
    public ResponseEntity<FriendAskResponse> createAsk(@RequestBody FriendAskCreate friendAskCreate,
                                                       UserSession userSession) {
        return ResponseEntity.created(null)
                .body(friendAskService.save(userSession.getId(), friendAskCreate));
    }

    @GetMapping
    public ResponseEntity<List<FriendResponse>> show(UserSession userSession){
        return ResponseEntity.ok(friendService.findAllFriendResponseByRelatingUserId(userSession.getId()));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody FriendCreate friendCreate) {
        friendService.save(friendCreate);
        return ResponseEntity.created(null).build();
    }

}
