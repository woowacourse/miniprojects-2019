package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.relation.RelationResponseDto;
import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.service.RelationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
public class RelationApiController {
    private final RelationService relationService;

    public RelationApiController(final RelationService relationService) {
        this.relationService = relationService;
    }

    @GetMapping
    public ResponseEntity<List<RelationResponseDto>> show(@SessionAttribute("loginUser") final UserResponseDto userResponseDto) {
        List<RelationResponseDto> friends = relationService.getFriends(userResponseDto.getId());
        List<RelationResponseDto> requestedFriends = relationService.getRequestedFriends(userResponseDto.getId());

        //error 남 Collections.unmodifiableList() 때문
        friends.addAll(requestedFriends);

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> getRelationship(@SessionAttribute("loginUser") final UserResponseDto userResponseDto,
                                        @PathVariable final Long toId) {
        return new ResponseEntity<>(relationService.getRelationShip(userResponseDto.getId(), toId), HttpStatus.OK);
    }

    @PostMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> addFriend(@SessionAttribute("loginUser") final UserResponseDto userResponseDto,
                                  @PathVariable final Long toId) {
        return new ResponseEntity<>(relationService.addFriend(userResponseDto.getId(), toId), HttpStatus.OK);
    }

    @PutMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> beFriend(@SessionAttribute("loginUser") final UserResponseDto userResponseDto,
                                 @PathVariable final Long toId) {
        return new ResponseEntity<>(relationService.beFriend(userResponseDto.getId(), toId), HttpStatus.OK);
    }

    @DeleteMapping("/{toId}")
    public ResponseEntity<RelationResponseDto> deleteRelation(@SessionAttribute("loginUser") final UserResponseDto userResponseDto,
                                       @PathVariable final Long toId) {
        return new ResponseEntity<>(relationService.delete(userResponseDto.getId(), toId), HttpStatus.OK);
    }
}
