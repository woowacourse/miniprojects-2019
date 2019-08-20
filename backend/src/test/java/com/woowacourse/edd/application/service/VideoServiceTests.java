package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.dto.VideoUpdateRequestDto;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.exceptions.InvalidContentsException;
import com.woowacourse.edd.exceptions.InvalidTitleException;
import com.woowacourse.edd.exceptions.InvalidYoutubeIdException;
import com.woowacourse.edd.exceptions.VideoNotFoundException;
import com.woowacourse.edd.repository.VideoRepository;
import com.woowacourse.edd.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTests {

    private static final Long DEFAULT_VIDEO_ID = 100L;
    private static final LocalDateTime DEFAULT_VIDEO_CREATE_DATE = LocalDateTime.of(2019, 7, 5, 15, 20);

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService service;

    private Video video;

    @BeforeEach
    void init() {
        video = spy(new Video("1234", "title", "contents"));
    }

    @Test
    void save() {
        when(video.getId()).thenReturn(DEFAULT_VIDEO_ID);
        when(video.getCreateDate()).thenReturn(DEFAULT_VIDEO_CREATE_DATE);

        VideoResponse videoResponse = new VideoResponse(DEFAULT_VIDEO_ID, "1234", "title", "contents", Utils.getFormedDate(DEFAULT_VIDEO_CREATE_DATE));
        when(videoRepository.save(any())).thenReturn(video);

        assertThat(service.save(new VideoSaveRequestDto("1234", "title", "contents"))).isEqualTo(videoResponse);
    }

    @Test
    void update() {
        when(video.getId()).thenReturn(DEFAULT_VIDEO_ID);
        when(video.getCreateDate()).thenReturn(DEFAULT_VIDEO_CREATE_DATE);
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertDoesNotThrow(() -> {
            service.update(DEFAULT_VIDEO_ID, new VideoUpdateRequestDto("4321", "title 2", "contents 2"));
            VideoResponse video = service.find(DEFAULT_VIDEO_ID);
            assertThat(video.getYoutubeId()).isEqualTo("4321");
            assertThat(video.getTitle()).isEqualTo("title 2");
            assertThat(video.getContents()).isEqualTo("contents 2");
        });
    }

    @Test
    void update_invalid_youtube_id() {
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertThrows(InvalidYoutubeIdException.class, () -> {
            service.update(DEFAULT_VIDEO_ID, new VideoUpdateRequestDto("", "title2", "contents2"));
        });
    }

    @Test
    void update_invalid_title() {
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertThrows(InvalidTitleException.class, () -> {
            service.update(DEFAULT_VIDEO_ID, new VideoUpdateRequestDto("1234", "", "contents2"));
        });
    }

    @Test
    void update_invalid_contents() {
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertThrows(InvalidContentsException.class, () -> {
            service.update(DEFAULT_VIDEO_ID, new VideoUpdateRequestDto("1234", "title", ""));
        });
    }

    @Test
    void delete() {
        when(videoRepository.findById(DEFAULT_VIDEO_ID)).thenReturn(Optional.of(video));

        assertDoesNotThrow(() -> {
            service.delete(DEFAULT_VIDEO_ID);
        });
    }

    @Test
    void delete_invalid_id() {
        assertThrows(VideoNotFoundException.class, () -> {
            service.delete(DEFAULT_VIDEO_ID + 1L);
        });
    }
}