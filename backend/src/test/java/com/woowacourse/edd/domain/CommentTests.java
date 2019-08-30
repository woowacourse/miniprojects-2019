package com.woowacourse.edd.domain;

import com.woowacourse.edd.exceptions.InvalidContentsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentTests extends BasicDomainTests {

    private Video video;
    private User author;

    @BeforeEach
    void setUp() {
        author = spy(new User("robby", "robby@robby.com", "P@ssw0rd"));
        video = spy(new Video("youtubeId##", "title", "contents", author));
    }

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            new Comment("contents", video, author);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidStrings")
    void contents_invalid(final String invalidString) {
        assertThrows(InvalidContentsException.class, () -> new Comment(invalidString, video, author));
    }

    @Test
    void update() {
        when(author.isNotMatch(any())).thenReturn(false);
        when(video.isNotMatch(any())).thenReturn(false);
        Comment comment = new Comment("contents", video, author);
        comment.update("updateContent", 1L, 1L);
        assertThat(comment.getContents()).isEqualTo("updateContent");
    }
}
