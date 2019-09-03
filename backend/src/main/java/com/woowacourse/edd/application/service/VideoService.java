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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VideoService {

    private final VideoInternalService videoInternalService;
    private final UserInternalService userInternalService;

    @Autowired
    public VideoService(VideoInternalService videoInternalService, UserInternalService userInternalService) {
        this.videoInternalService = videoInternalService;
        this.userInternalService = userInternalService;
    }

    public VideoResponse save(VideoSaveRequestDto requestDto, Long id) {
        User user = userInternalService.findById(id);
        Video video = videoInternalService.save(VideoConverter.toEntity(requestDto, user));
        return VideoConverter.toResponse(video);
    }

    public Page<VideoPreviewResponse> findByPageRequest(Pageable pageable) {
        return videoInternalService.findAll(pageable).map(VideoConverter::toPreviewResponse);
    }

    public VideoResponse findById(long id) {
        Video video = videoInternalService.findById(id);
        return VideoConverter.toResponse(video);
    }

    public VideoUpdateResponse update(Long id, VideoUpdateRequestDto requestDto, Long loginedUserId) {
        Video video = videoInternalService.update(id, VideoConverter.escapeUpdateRequestDto(requestDto), loginedUserId);
        return VideoConverter.toUpdateResponse(video);
    }

    public VideoResponse findWithIncreaseViewCount(Long videoId) {
        videoInternalService.updateViewCount(videoId);
        return VideoConverter.toResponse(videoInternalService.findById(videoId));
    }

    public void delete(Long id, Long logindedUserId) {
        videoInternalService.delete(id, logindedUserId);
    }

    public List<VideoPreviewResponse> findByCreatorId(Long creatorId) {
        return videoInternalService.findByCreatorId(creatorId).stream()
            .map(VideoConverter::toPreviewResponse)
            .collect(Collectors.toList());
    }
}
