package com.woowacourse.sunbook.presentation.excpetion;

public class NotFoundArticleException extends RuntimeException {
    public NotFoundArticleException() {
        super("찾을 수 없는 게시글입니다.");
    }
}
