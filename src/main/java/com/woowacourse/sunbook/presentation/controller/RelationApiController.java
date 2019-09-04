package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.relation.RelationResponseDto;
import com.woowacourse.sunbook.application.service.RelationService;
import com.woowacourse.sunbook.presentation.support.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class RelationApiController {
    private final RelationService relationService;

    public RelationApiController(final RelationService relationService) {
        this.relationService = relationService;
    }

    @GetMapping("/friends")
    public ResponseEntity<List<RelationResponseDto>> showFriends(LoginUser loginUser) {
        List<RelationResponseDto> friends = relationService.getFriends(loginUser.getId());

        return ResponseEntity.ok().body(friends);
    }

    @GetMapping("/friends/requested")
    public ResponseEntity<List<RelationResponseDto>> showRequestedFriends(LoginUser loginUser) {
        List<RelationResponseDto> requestedFriends = relationService.getRequestedFriends(loginUser.getId());

        return ResponseEntity.ok().body(requestedFriends);
    }

    @GetMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> getRelationship(LoginUser loginUser,
                                        @PathVariable final Long toId) {
        return ResponseEntity.ok().body(relationService.getRelationShip(loginUser.getId(), toId));
    }

    @PostMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> addFriend(LoginUser loginUser,
                                  @PathVariable final Long toId) {
        return ResponseEntity.ok().body(relationService.addFriend(loginUser.getId(), toId));
    }

    @PutMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> beFriend(LoginUser loginUser,
                                 @PathVariable final Long toId) {
        return ResponseEntity.ok().body(relationService.beFriend(loginUser.getId(), toId));
    }

    @DeleteMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> deleteRelation(LoginUser loginUser,
                                       @PathVariable final Long toId) {
        return ResponseEntity.ok().body(relationService.delete(loginUser.getId(), toId));
    }
}
