package com.woowacourse.edd.application.converter;

import com.nhncorp.lucy.security.xss.XssPreventer;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.dto.VideoUpdateRequestDto;
import com.woowacourse.edd.application.response.VideoPreviewResponse;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.application.response.VideoUpdateResponse;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.domain.Video;

public class VideoConverter {

    public static Video toEntity(VideoSaveRequestDto requestDto, User creator) {
        return new Video(XssPreventer.escape(requestDto.getYoutubeId()), XssPreventer.escape(requestDto.getTitle()), XssPreventer.escape(requestDto.getContents()), creator);
    }

    public static VideoResponse toResponse(Video video) {
        String date = video.getCreateDate().toString();
        VideoResponse.CreatorResponse creatorResponse = new VideoResponse.CreatorResponse(video.getCreator().getId(), video.getCreator().getName());
        return new VideoResponse(video.getId(), video.getYoutubeId(), video.getTitle(), video.getContents(), date, creatorResponse, video.getViewCount());
    }

    public static VideoPreviewResponse toPreviewResponse(Video video) {
        String date = video.getCreateDate().toString();
        VideoResponse.CreatorResponse creatorResponse = new VideoResponse.CreatorResponse(video.getCreator().getId(), video.getCreator().getName());
        return new VideoPreviewResponse(video.getId(), video.getYoutubeId(), video.getTitle(), date, creatorResponse, video.getViewCount());
    }

    public static VideoUpdateResponse toUpdateResponse(Video video) {
        return new VideoUpdateResponse(video.getId());
    }

    public static VideoUpdateRequestDto escapeUpdateRequestDto(VideoUpdateRequestDto updateRequestDto) {
        return new VideoUpdateRequestDto(
            XssPreventer.escape(updateRequestDto.getYoutubeId()),
            XssPreventer.escape(updateRequestDto.getTitle()),
            XssPreventer.escape(updateRequestDto.getContents()));
    }
}
