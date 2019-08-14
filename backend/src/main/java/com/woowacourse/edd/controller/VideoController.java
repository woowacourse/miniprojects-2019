package com.woowacourse.edd.controller;

import com.woowacourse.edd.service.VideoService;
import com.woowacourse.edd.service.dto.VideoInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public ResponseEntity<List<VideoInfoResponse>> findVideosByFilter(@RequestParam String filter, @RequestParam int page, @RequestParam int limit) {
        if ("date".equals(filter)) {
            return new ResponseEntity<>(videoService.findVideosByDate(page, limit), HttpStatus.OK);
        }
        return new ResponseEntity<>(videoService.findVideosByViewNumbers(page, limit), HttpStatus.OK);
    }
}
