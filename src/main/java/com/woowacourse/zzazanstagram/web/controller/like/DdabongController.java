package com.woowacourse.zzazanstagram.web.controller.like;

import com.woowacourse.zzazanstagram.model.like.dto.DdabongResponse;
import com.woowacourse.zzazanstagram.model.like.service.DdabongService;
import com.woowacourse.zzazanstagram.model.member.MemberSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DdabongController {
    private final DdabongService ddabongService;

    public DdabongController(DdabongService ddabongService) {
        this.ddabongService = ddabongService;
    }

    @PostMapping("/articles/{articleId}/ddabongs")
    public ResponseEntity<DdabongResponse> clickDdabong(@PathVariable Long articleId, MemberSession memberSession) {
        DdabongResponse ddabongResponse = ddabongService.saveOrRemove(articleId, memberSession.getEmail());
        return new ResponseEntity<>(ddabongResponse, HttpStatus.OK);
    }
}
