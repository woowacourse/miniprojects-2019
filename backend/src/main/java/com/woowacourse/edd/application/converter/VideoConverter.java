package com.woowacourse.edd.application.converter;

import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.response.VideoPreviewResponse;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.domain.Video;

import java.time.format.DateTimeFormatter;

public class VideoConverter {

    public Video toEntity(VideoSaveRequestDto requestDto) {
        return new Video(requestDto.getYoutubeId(), requestDto.getTitle(), requestDto.getContents());
    }

    public VideoResponse toResponse(Video video) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String date = video.getCreateDate().format(format);
        return new VideoResponse(video.getId(), video.getYoutubeId(), video.getTitle(), video.getContents(), date);
    }

    public VideoPreviewResponse toPreviewResponse(Video video) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String date = video.getCreateDate().format(format);
        return new VideoPreviewResponse(video.getId(), video.getYoutubeId(), video.getTitle(), date);
    }
}
