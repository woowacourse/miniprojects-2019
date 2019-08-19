package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.domain.vo.Contents;
import com.woowacourse.edd.domain.vo.Title;
import com.woowacourse.edd.domain.vo.YoutubeId;
import com.woowacourse.edd.repository.VideoRepository;
import com.woowacourse.edd.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    private static final Long DEFAULT_VIDEO_ID = 100L;
    private static final LocalDateTime DEFAULT_VIDEO_CREATE_DATE = LocalDateTime.of(2019, 7, 5, 15, 20);

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService service;

    private Video video;

    @BeforeEach
    void init() {
        video = spy(new Video(new YoutubeId("1234"), new Title("title"), new Contents("contents")));
        when(video.getId()).thenReturn(100L);
        when(video.getCreateDate()).thenReturn(DEFAULT_VIDEO_CREATE_DATE);
    }

    @Test
    void save() {
        VideoResponse videoResponse = new VideoResponse(DEFAULT_VIDEO_ID, "1234", "title", "contents", Utils.getFormedDate(DEFAULT_VIDEO_CREATE_DATE));
        when(videoRepository.save(any())).thenReturn(video);

        assertThat(service.save(new VideoSaveRequestDto("1234", "title", "contents"))).isEqualTo(videoResponse);
    }
}