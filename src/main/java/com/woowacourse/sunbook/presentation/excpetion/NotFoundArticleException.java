package com.woowacourse.sunbook.presentation.excpetion;

public class NotFoundArticleException extends RuntimeException {
    private static final String NOT_FOUND_ARTICLE_EXCEPTION_MESSAGE = "찾을 수 없는 게시글입니다.";

    public NotFoundArticleException() {
        super(NOT_FOUND_ARTICLE_EXCEPTION_MESSAGE);
    }
}
