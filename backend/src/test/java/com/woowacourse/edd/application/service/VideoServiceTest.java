package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.VideoConverter;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.domain.vo.Contents;
import com.woowacourse.edd.domain.vo.Title;
import com.woowacourse.edd.domain.vo.YoutubeId;
import com.woowacourse.edd.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {
    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoConverter videoConverter;

    @InjectMocks
    private VideoService service;

    @Test
    void save() {
        Video video = new Video(new YoutubeId("1234"), new Title("title"), new Contents("contents"));
        VideoResponse videoResponse = new VideoResponse(100L, "1234", "title", "contents", getFormedDate());
        when(videoRepository.save(any())).thenReturn(video);
        when(videoConverter.toEntity(any())).thenReturn(video);
        when(videoConverter.toResponse(any())).thenReturn(videoResponse);

        assertThat(service.save(new VideoSaveRequestDto("1234", "title", "contents"))).isEqualTo(videoResponse);
    }

    private String getFormedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        return now.format(formatter);
    }
}