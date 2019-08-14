package com.woowacourse.edd.domain.vo;

import com.woowacourse.edd.exceptions.InvalidYoutubeIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class YoutubeIdTest {

    @Test
    void 올바른_youtube_id_검사(){
        assertDoesNotThrow(()-> new YoutubeId("abc"));
    }

    @Test
    void youtubeId가_null_검사() {
        assertThrows(InvalidYoutubeIdException.class, () -> new YoutubeId(null));
    }

    @Test
    void youtubeId가_공백_검사() {
        assertThrows(InvalidYoutubeIdException.class, () -> new YoutubeId(""));
    }
}
