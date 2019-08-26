package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.domain.relation.Relationship;
import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class RelationApiControllerTest extends TestTemplate {

    @Test
    void 상대방과_관계_확인() {
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.GET, "/api/friend/2", Relationship.NONE, HttpStatus.OK, sessionId))
                .jsonPath("$..relationship").isEqualTo("NONE");
    }
}