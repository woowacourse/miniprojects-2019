package com.woowacourse.edd.domain;

import com.woowacourse.edd.exceptions.InvalidContentsException;
import com.woowacourse.edd.exceptions.InvalidTitleException;
import com.woowacourse.edd.exceptions.InvalidYoutubeIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("invalidStrings")
    void youtubeId_invalid(final String invalidString) {
        assertThrows(InvalidYoutubeIdException.class, () -> new Video(invalidString, VALID_TITLE, VALID_CONTENTS, creator));
    }

    @ParameterizedTest
    @MethodSource("invalidStrings")
    void title_invalid(final String invalidString) {
        assertThrows(InvalidTitleException.class, () -> new Video(VALID_YOUTUBE_ID, invalidString, VALID_CONTENTS, creator));
    }

    @ParameterizedTest
    @MethodSource("invalidStrings")
    void contents_invalid(final String invalidString) {
        assertThrows(InvalidContentsException.class, () -> new Video(VALID_YOUTUBE_ID, VALID_TITLE, invalidString, creator));
    }

    private static Stream<String> invalidStrings() {
        return Stream.of(EMPTY, BLANK, null);
    }
}