package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.VideoUpdateRequestDto;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.exceptions.InvalidContentsException;
import com.woowacourse.edd.exceptions.InvalidTitleException;
import com.woowacourse.edd.exceptions.InvalidYoutubeIdException;
import com.woowacourse.edd.exceptions.VideoNotFoundException;
import com.woowacourse.edd.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoInternalServiceTests {

    private static final Long DEFAULT_VIDEO_ID = 100L;

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoInternalService videoInternalService;

    private Video video;
    private User creator;

    @BeforeEach
    void init() {
        creator = spy(new User("name", "name@gmail.com", "p@ssW0rd", false));
        video = spy(new Video("1234", "title", "contents", creator));
    }

    @Test
    void save() {
        when(creator.getId()).thenReturn(1L);
        when(videoRepository.save(any())).thenReturn(video);

        Video target = new Video("1234", "title", "contents", creator);
        Video src = videoInternalService.save(video);

        assertThat(src.getTitle()).isEqualTo(target.getTitle());
        assertThat(src.getYoutubeId()).isEqualTo(target.getYoutubeId());
        assertThat(src.getContents()).isEqualTo(target.getContents());
        assertThat(src.getCreator().getId()).isEqualTo(target.getCreator().getId());
        assertThat(src.getCreator().getName()).isEqualTo(target.getCreator().getName());
    }

    @Test
    void update() {
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertDoesNotThrow(() -> {
            videoInternalService.update(DEFAULT_VIDEO_ID, new VideoUpdateRequestDto("4321", "title 2", "contents 2"));
            Video video = videoInternalService.findById(DEFAULT_VIDEO_ID);
            assertThat(video.getYoutubeId()).isEqualTo("4321");
            assertThat(video.getTitle()).isEqualTo("title 2");
            assertThat(video.getContents()).isEqualTo("contents 2");
        });
    }

    @Test
    void update_invalid_youtube_id() {
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertThrows(InvalidYoutubeIdException.class, () -> {
            videoInternalService.update(DEFAULT_VIDEO_ID, new VideoUpdateRequestDto("", "title2", "contents2"));
        });
    }

    @Test
    void update_invalid_title() {
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertThrows(InvalidTitleException.class, () -> {
            videoInternalService.update(DEFAULT_VIDEO_ID, new VideoUpdateRequestDto("1234", "", "contents2"));
        });
    }

    @Test
    void update_invalid_contents() {
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertThrows(InvalidContentsException.class, () -> {
            videoInternalService.update(DEFAULT_VIDEO_ID, new VideoUpdateRequestDto("1234", "title", ""));
        });
    }

    @Test
    void delete() {
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertDoesNotThrow(() -> {
            videoInternalService.delete(DEFAULT_VIDEO_ID);
        });
    }

    @Test
    void delete_invalid_id() {
        assertThrows(VideoNotFoundException.class, () -> {
            videoInternalService.delete(DEFAULT_VIDEO_ID + 1L);
        });
    }
}