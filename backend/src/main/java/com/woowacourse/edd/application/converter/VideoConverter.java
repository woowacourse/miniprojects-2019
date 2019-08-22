package com.woowacourse.edd.application.converter;

import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.response.VideoPreviewResponse;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.application.response.VideoUpdateResponse;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.domain.Video;

import java.time.format.DateTimeFormatter;

public class VideoConverter {

    public Video toEntity(VideoSaveRequestDto requestDto, User creator) {
        return new Video(requestDto.getYoutubeId(), requestDto.getTitle(), requestDto.getContents(), creator);
    }

    public VideoResponse toResponse(Video video) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String date = video.getCreateDate().format(format);
        VideoResponse.CreatorResponse creatorResponse = new VideoResponse.CreatorResponse(video.getCreator().getId(), video.getCreator().getName());
        return new VideoResponse(video.getId(), video.getYoutubeId(), video.getTitle(), video.getContents(), date, creatorResponse);
    }

    public VideoPreviewResponse toPreviewResponse(Video video) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String date = video.getCreateDate().format(format);
        VideoResponse.CreatorResponse creatorResponse = new VideoResponse.CreatorResponse(video.getCreator().getId(), video.getCreator().getName());
        return new VideoPreviewResponse(video.getId(), video.getYoutubeId(), video.getTitle(), date, creatorResponse);
    }

    public VideoUpdateResponse toUpdateResponse(Video video) {
        return new VideoUpdateResponse(video.getId());
    }
}
