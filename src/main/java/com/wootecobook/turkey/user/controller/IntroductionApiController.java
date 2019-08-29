package com.wootecobook.turkey.user.controller;

import com.wootecobook.turkey.commons.resolver.LoginUser;
import com.wootecobook.turkey.commons.resolver.UserSession;
import com.wootecobook.turkey.user.service.IntroductionService;
import com.wootecobook.turkey.user.service.dto.IntroductionRequest;
import com.wootecobook.turkey.user.service.dto.IntroductionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/introduction")
public class IntroductionApiController {

    private final IntroductionService introductionService;

    public IntroductionApiController(final IntroductionService introductionService) {
        this.introductionService = introductionService;
    }

    @GetMapping
    public ResponseEntity<IntroductionResponse> show(@PathVariable Long userId) {
        return ResponseEntity.ok(introductionService.findIntroductionResponseByUserId(userId));
    }

    @PutMapping
    public ResponseEntity<IntroductionResponse> update(@RequestBody IntroductionRequest introductionRequest,
                                                       @LoginUser UserSession userSession) {
        return ResponseEntity.ok(introductionService.update(introductionRequest, userSession.getId()));
    }
}
