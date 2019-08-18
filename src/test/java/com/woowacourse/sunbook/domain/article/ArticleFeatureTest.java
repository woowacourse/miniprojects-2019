package com.woowacourse.sunbook.domain.article;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleFeatureTest {
    private static final String CONTENTS = "SunBook Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";
    private static final String WRONG_VIDEO_URL = "ABCDEFG";
    private static final String EMPTY = "";

    @Test
    void ArticleFeature_정상_생성() {
        ArticleFeature articleFeature = new ArticleFeature(CONTENTS, IMAGE_URL, VIDEO_URL);
        assertThat(articleFeature).isEqualTo(new ArticleFeature(CONTENTS, IMAGE_URL, VIDEO_URL));
    }

    @Test
    void Url_비정상_생성() {
        assertThrows(IllegalArgumentException.class, () -> new ArticleFeature(CONTENTS, IMAGE_URL, WRONG_VIDEO_URL));
    }

    @Test
    void ArticleFeature_비정상_생성() {
        assertThrows(IllegalArgumentException.class, () -> new ArticleFeature(EMPTY, EMPTY, EMPTY));
    }
}
