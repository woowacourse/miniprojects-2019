package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.CommentRequestDto;
import com.woowacourse.edd.domain.Comment;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.exceptions.InvalidAccessException;
import com.woowacourse.edd.exceptions.UnauthorizedAccessException;
import com.woowacourse.edd.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentInternalServiceTests {

    private User user;
    private Video video;
    private Comment comment;
    private CommentRequestDto commentRequestDto;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentInternalService commentInternalService;

    @BeforeEach
    void setUp() {
        user = spy(new User("robby", "kangmin789@naver.com", "P@ssW0rd"));
        video = spy(new Video("youtubeId", "title", "videoContents", user));
        comment = new Comment("contents", video, user);
        commentRequestDto = new CommentRequestDto("updateContents");
    }

    @Test
    void update() {
        when(user.isNotMatch(any())).thenReturn(false);
        when(video.isNotMatch(any())).thenReturn(false);
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        Comment updateComment = commentInternalService.update(1L, 1L, 1L, commentRequestDto);

        assertThat(updateComment.getContents()).isEqualTo("updateContents");
    }

    @Test
    void update_invalid_Video() {
        when(user.isNotMatch(any())).thenReturn(false);
        when(video.isNotMatch(any())).thenReturn(true);
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        assertThrows(InvalidAccessException.class, () -> {
            commentInternalService.update(1L, 1L, 1L, commentRequestDto);
        });
    }

    @Test
    void update_invalid_user() {
        when(user.isNotMatch(any())).thenReturn(true);
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        assertThrows(UnauthorizedAccessException.class, () -> {
            commentInternalService.update(1L, 1L, 1L, commentRequestDto);
        });
    }
}
