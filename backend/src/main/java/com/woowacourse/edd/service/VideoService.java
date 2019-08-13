package com.woowacourse.edd.service;

import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.repository.VideoRepository;
import com.woowacourse.edd.service.dto.VideoInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class VideoService {

    private VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<VideoInfoResponse> findVideosByDate(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createDate").descending());
        Page<Video> foundVideos = videoRepository.findAll(pageRequest);

        return foundVideos.getContent().stream()
                .map(VideoInfoResponse::of)
                .collect(toList());
    }

    public List<VideoInfoResponse> findVideosByViewNumbers(int page, int limit) {
        return null;
    }
}
