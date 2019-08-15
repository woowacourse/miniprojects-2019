package com.woowacourse.edd.application.converter;

import com.woowacourse.edd.application.dto.VideoPreviewResponse;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.domain.vo.Contents;
import com.woowacourse.edd.domain.vo.Title;
import com.woowacourse.edd.domain.vo.YoutubeId;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class VideoConverter {

    public Video toEntity(VideoSaveRequestDto requestDto) {
        return new Video(new YoutubeId(requestDto.getYoutubeId()), new Title(requestDto.getTitle()), new Contents(requestDto.getContents()));
    }

    public VideoResponse toResponse(Video video) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String date = video.getCreateDate().format(format);
        return new VideoResponse(video.getId(), video.getYoutubeId().getYoutubeId(), video.getTitle().getTitle(), video.getContents().getContents(), date);
    }

    public VideoPreviewResponse toPreviewResponse(Video video) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String date = video.getCreateDate().format(format);
        return new VideoPreviewResponse(video.getId(), video.getYoutubeId().getYoutubeId(), video.getTitle().getTitle(), date);
    }
}
