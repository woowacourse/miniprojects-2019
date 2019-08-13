package com.woowacourse.sunbook.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleTest {

    private static final String CONTENTS = "SunBook Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";
    private static final String EMPTY = "";

    @Test
    void 게시글_정상_생성_이미지_비디오_존재() {
        assertDoesNotThrow(() -> new Article(CONTENTS, IMAGE_URL, VIDEO_URL));
    }

    @Test
    void 게시글_정상_생성_내용없고_이미지_존재_비디오_존재() {
        assertDoesNotThrow(() -> new Article(EMPTY, IMAGE_URL, VIDEO_URL));
    }

    @Test
    void 게시글_정상_생성_이미지_없고_비디오_존재() {
        assertDoesNotThrow(() -> new Article(CONTENTS, EMPTY, VIDEO_URL));
    }

    @Test
    void 게시글_정상_생성_이미지_존재_비디오_없음() {
        assertDoesNotThrow(() -> new Article(CONTENTS, IMAGE_URL, EMPTY));
    }

    @Test
    void 게시글_정상_생성_내용만_있음() {
        assertDoesNotThrow(() -> new Article(CONTENTS, EMPTY, EMPTY));
    }

    @Test
    void 게시글_정상_생성_이미지만_있음() {
        assertDoesNotThrow(() -> new Article(EMPTY, IMAGE_URL, EMPTY));
    }

    @Test
    void 게시글_정상_생성_비디오만_있음() {
        assertDoesNotThrow(() -> new Article(EMPTY, EMPTY, VIDEO_URL));
    }

    @Test
    void 게시글_생성_오류_아무것도_없음() {
        assertThrows(IllegalArgumentException.class, () -> new Article(EMPTY, EMPTY, EMPTY));
    }
}