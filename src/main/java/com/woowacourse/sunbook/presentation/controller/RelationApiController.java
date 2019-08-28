package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.relation.RelationResponseDto;
import com.woowacourse.sunbook.application.service.RelationService;
import com.woowacourse.sunbook.presentation.support.LoginUser;
import org.springframework.http.HttpStatus;
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

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/friends/requested")
    public ResponseEntity<List<RelationResponseDto>> showRequestedFriends(LoginUser loginUser) {
        List<RelationResponseDto> requestedFriends = relationService.getRequestedFriends(loginUser.getId());

        return new ResponseEntity<>(requestedFriends, HttpStatus.OK);
    }

    @GetMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> getRelationship(LoginUser loginUser,
                                        @PathVariable final Long toId) {
        return new ResponseEntity<>(relationService.getRelationShip(loginUser.getId(), toId), HttpStatus.OK);
    }

    @PostMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> addFriend(LoginUser loginUser,
                                  @PathVariable final Long toId) {
        return new ResponseEntity<>(relationService.addFriend(loginUser.getId(), toId), HttpStatus.OK);
    }

    @PutMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> beFriend(LoginUser loginUser,
                                 @PathVariable final Long toId) {
        return new ResponseEntity<>(relationService.beFriend(loginUser.getId(), toId), HttpStatus.OK);
    }

    @DeleteMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> deleteRelation(LoginUser loginUser,
                                       @PathVariable final Long toId) {
        return new ResponseEntity<>(relationService.delete(loginUser.getId(), toId), HttpStatus.OK);
    }
}
