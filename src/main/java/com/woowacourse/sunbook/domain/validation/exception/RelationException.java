package com.woowacourse.sunbook.domain.validation.exception;

public class RelationException extends RuntimeException {

	public RelationException() {
		super("이미 신청했습니다");
	}

	public RelationException(String message) {
		super(message);
	}
}
