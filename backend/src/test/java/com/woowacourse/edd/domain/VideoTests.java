package com.woowacourse.edd.domain;

import com.woowacourse.edd.exceptions.InvalidContentsException;
import com.woowacourse.edd.exceptions.InvalidTitleException;
import com.woowacourse.edd.exceptions.InvalidYoutubeIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VideoTests {

    private static final String VALID_YOUTUBE_ID = "abc";
    private static final String VALID_TITLE = "abcd";
    private static final String VALID_CONTENTS = "abcde";
    private static final String EMPTY = "";
    private static final String BLANK = " ";

    @Test
    void create_video() {
        assertDoesNotThrow(() -> new Video(VALID_YOUTUBE_ID, VALID_TITLE, VALID_CONTENTS));
    }

    @Test
    void youtubeId_empty() {
        assertThrows(InvalidYoutubeIdException.class, () -> new Video(EMPTY, VALID_TITLE, VALID_CONTENTS));
    }

    @Test
    void youtubeId__null() {
        assertThrows(InvalidYoutubeIdException.class, () -> new Video(null, VALID_TITLE, VALID_CONTENTS));
    }

    @Test
    void youtubeId__blank() {
        assertThrows(InvalidYoutubeIdException.class, () -> new Video(BLANK, VALID_TITLE, VALID_CONTENTS));
    }

    @Test
    void title_empty() {
        assertThrows(InvalidTitleException.class, () -> new Video(VALID_YOUTUBE_ID, EMPTY, VALID_CONTENTS));
    }

    @Test
    void title_null() {
        assertThrows(InvalidTitleException.class, () -> new Video(VALID_YOUTUBE_ID, null, VALID_CONTENTS));
    }

    @Test
    void title_blank() {
        assertThrows(InvalidTitleException.class, () -> new Video(VALID_YOUTUBE_ID, BLANK, VALID_CONTENTS));
    }

    @Test
    void contents_empty() {
        assertThrows(InvalidContentsException.class, () -> new Video(VALID_YOUTUBE_ID, VALID_TITLE, EMPTY));
    }

    @Test
    void contents_null() {
        assertThrows(InvalidContentsException.class, () -> new Video(VALID_YOUTUBE_ID, VALID_TITLE, null));
    }

    @Test
    void contents_blank() {
        assertThrows(InvalidContentsException.class, () -> new Video(VALID_YOUTUBE_ID, VALID_TITLE, BLANK));
    }


}