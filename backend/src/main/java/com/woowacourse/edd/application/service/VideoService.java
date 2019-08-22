package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.VideoConverter;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.dto.VideoUpdateRequestDto;
import com.woowacourse.edd.application.response.VideoPreviewResponse;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.application.response.VideoUpdateResponse;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.domain.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    private final VideoInternalService videoInternalService;
    private final UserInternalService userInternalService;
    private final VideoConverter videoConverter = new VideoConverter();

    @Autowired
    public VideoService(VideoInternalService videoInternalService, UserInternalService userInternalService) {
        this.videoInternalService = videoInternalService;
        this.userInternalService = userInternalService;
    }

    public VideoResponse save(VideoSaveRequestDto requestDto, Long id) {
        User user = userInternalService.findById(id);
        Video video = videoInternalService.save(videoConverter.toEntity(requestDto, user));
        return videoConverter.toResponse(video);
    }

    public Page<VideoPreviewResponse> findByPageRequest(Pageable pageable) {
        return videoInternalService.findAll(pageable).map(videoConverter::toPreviewResponse);
    }

    public VideoResponse findById(long id) {
        Video video = videoInternalService.findById(id);
        return videoConverter.toResponse(video);
    }

    public VideoUpdateResponse update(Long id, VideoUpdateRequestDto requestDto) {
        Video video = videoInternalService.update(id, requestDto);
        return videoConverter.toUpdateResponse(video);
    }

    public void delete(Long id) {
        videoInternalService.delete(id);
    }
}
