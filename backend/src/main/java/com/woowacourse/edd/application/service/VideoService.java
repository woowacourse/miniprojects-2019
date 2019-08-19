package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.VideoConverter;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.response.VideoPreviewResponse;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.exceptions.VideoNotFoundException;
import com.woowacourse.edd.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VideoService {

    private final VideoRepository videoRepository;

    private final VideoConverter videoConverter = new VideoConverter();

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public VideoResponse save(VideoSaveRequestDto requestDto) {
        Video video = videoRepository.save(videoConverter.toEntity(requestDto));

        return videoConverter.toResponse(video);
    }

    @Transactional(readOnly = true)
    public Page<VideoPreviewResponse> findByPageRequest(Pageable pageable) {
        return videoRepository.findAll(pageable).map(videoConverter::toPreviewResponse);
    }

    @Transactional(readOnly = true)
    public VideoResponse find(long id) {
        Video video = findById(id);
        return videoConverter.toResponse(video);
    }

    private Video findById(long id) {
        return videoRepository.findById(id).orElseThrow(VideoNotFoundException::new);
    }
}
