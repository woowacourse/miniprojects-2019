package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.VideoConverter;
import com.woowacourse.edd.application.dto.VideoPreviewResponse;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.exceptions.VideoNotFoundException;
import com.woowacourse.edd.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class VideoService {

    private static final String CREATE_DATE = "createDate";
    private final VideoRepository videoRepository;
    private final VideoConverter videoConverter;

    @Autowired
    public VideoService(VideoRepository videoRepository, VideoConverter videoConverter) {
        this.videoRepository = videoRepository;
        this.videoConverter = videoConverter;
    }

    public VideoResponse save(VideoSaveRequestDto requestDto) {
        Video video = videoRepository.save(videoConverter.toEntity(requestDto));

        return videoConverter.toResponse(video);
    }

    public List<VideoPreviewResponse> findVideosByDate(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(CREATE_DATE).descending());
        Page<Video> foundVideos = videoRepository.findAll(pageRequest);

        return foundVideos.getContent().stream()
            .map(videoConverter::toPreviewResponse)
            .collect(toList());
    }

    public VideoResponse find(long id) {
        Video video = findById(id);
        return videoConverter.toResponse(video);
    }

    private Video findById(long id) {
        return videoRepository.findById(id).orElseThrow(VideoNotFoundException::new);
    }
}
