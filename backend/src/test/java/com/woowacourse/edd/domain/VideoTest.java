package com.woowacourse.edd.domain;

import com.woowacourse.edd.domain.vo.Contents;
import com.woowacourse.edd.domain.vo.Title;
import com.woowacourse.edd.domain.vo.YoutubeId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class VideoTest {
    @Test
    void create_video_test() {
        assertDoesNotThrow(() -> new Video(new YoutubeId("sadfsadfasdf"), new Title("title"), new Contents("contents")));
    }
}