package com.woowacourse.sunbook.seongmo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(Long userId) {
        super("찾을 수 없는 유저입니다.");
        log.info("Requested User Id: {}", userId);
    }
}
