package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.VideoPreviewResponse;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.application.service.VideoService;
import com.woowacourse.edd.exceptions.InvalidFilterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/videos")
public class VideoController {

    private static final String DATE = "date";
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> findVideo(@PathVariable long id) {
        VideoResponse videoResponse = videoService.find(id);
        return ResponseEntity.ok(videoResponse);
    }

    @GetMapping
    public ResponseEntity<List<VideoPreviewResponse>> findVideosByFilter(@RequestParam String filter, @RequestParam int page, @RequestParam int limit) {
        if (DATE.equals(filter)) {
            return new ResponseEntity<>(videoService.findVideosByDate(page, limit), HttpStatus.OK);
        }
        throw new InvalidFilterException();
    }

    @PostMapping
    public ResponseEntity<VideoResponse> saveVideo(@RequestBody VideoSaveRequestDto requestDto) {
        VideoResponse response = videoService.save(requestDto);
        return ResponseEntity.ok(response);
    }
}
