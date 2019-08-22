package com.woowacourse.edd.domain;

import com.woowacourse.edd.exceptions.InvalidContentsException;
import com.woowacourse.edd.exceptions.InvalidTitleException;
import com.woowacourse.edd.exceptions.InvalidYoutubeIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VideoTests {

    private static final String VALID_YOUTUBE_ID = "abc";
    private static final String VALID_TITLE = "abcd";
    private static final String VALID_CONTENTS = "abcde";
    private static final String EMPTY = "";
    private static final String BLANK = " ";

    private static User creator;

    @BeforeEach
    void setUp() {
        creator = new User("name", "name@gamil.com", "P@ssw0rd", false);
    }

    @Test
    void create_video() {
        assertDoesNotThrow(() -> new Video(VALID_YOUTUBE_ID, VALID_TITLE, VALID_CONTENTS, creator));
    }

    @Test
    void youtubeId_empty() {
        assertThrows(InvalidYoutubeIdException.class, () -> new Video(EMPTY, VALID_TITLE, VALID_CONTENTS, creator));
    }

    @Test
    void youtubeId__null() {
        assertThrows(InvalidYoutubeIdException.class, () -> new Video(null, VALID_TITLE, VALID_CONTENTS, creator));
    }

    @Test
    void youtubeId__blank() {
        assertThrows(InvalidYoutubeIdException.class, () -> new Video(BLANK, VALID_TITLE, VALID_CONTENTS, creator));
    }

    @Test
    void title_empty() {
        assertThrows(InvalidTitleException.class, () -> new Video(VALID_YOUTUBE_ID, EMPTY, VALID_CONTENTS, creator));
    }

    @Test
    void title_null() {
        assertThrows(InvalidTitleException.class, () -> new Video(VALID_YOUTUBE_ID, null, VALID_CONTENTS, creator));
    }

    @Test
    void title_blank() {
        assertThrows(InvalidTitleException.class, () -> new Video(VALID_YOUTUBE_ID, BLANK, VALID_CONTENTS, creator));
    }

    @Test
    void contents_empty() {
        assertThrows(InvalidContentsException.class, () -> new Video(VALID_YOUTUBE_ID, VALID_TITLE, EMPTY, creator));
    }

    @Test
    void contents_null() {
        assertThrows(InvalidContentsException.class, () -> new Video(VALID_YOUTUBE_ID, VALID_TITLE, null, creator));
    }

    @Test
    void contents_blank() {
        assertThrows(InvalidContentsException.class, () -> new Video(VALID_YOUTUBE_ID, VALID_TITLE, BLANK, creator));
    }


}