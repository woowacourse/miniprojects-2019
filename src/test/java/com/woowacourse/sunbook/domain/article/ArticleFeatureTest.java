package com.woowacourse.sunbook.domain.article;

import com.woowacourse.sunbook.domain.comment.CommentFeature;
import com.woowacourse.sunbook.domain.fileurl.FileUrl;
import com.woowacourse.sunbook.domain.fileurl.exception.InvalidUrlException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleFeatureTest {
    private static final String CONTENTS = "SunBook Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";
    private static final String WRONG_VIDEO_URL = "ABCDEFG";
    private static final String EMPTY = "";

    private static final CommentFeature commentFeature = new CommentFeature(CONTENTS);
    private static final FileUrl imageUrl = new FileUrl(IMAGE_URL);
    private static final FileUrl videoUrl = new FileUrl(VIDEO_URL);
    private static final CommentFeature emptyContents = new CommentFeature(EMPTY);
    private static final FileUrl emptyUrl = new FileUrl(EMPTY);

    @Test
    void ArticleFeature_정상_생성() {
        ArticleFeature articleFeature = new ArticleFeature(commentFeature, imageUrl, videoUrl);
        assertThat(articleFeature).isEqualTo(new ArticleFeature(commentFeature, imageUrl, videoUrl));
    }

    @Test
    void Url_비정상_생성() {
        assertThrows(InvalidUrlException.class, () -> new ArticleFeature(commentFeature, imageUrl, new FileUrl(WRONG_VIDEO_URL)));
    }

    @Test
    void ArticleFeature_비정상_생성() {
        assertThrows(IllegalArgumentException.class, () -> new ArticleFeature(emptyContents, emptyUrl, emptyUrl));
    }
}
