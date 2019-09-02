package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.dto.VideoUpdateRequestDto;
import com.woowacourse.edd.application.response.SessionUser;
import com.woowacourse.edd.application.response.VideoPreviewResponse;
import com.woowacourse.edd.application.response.VideoResponse;
import com.woowacourse.edd.application.response.VideoUpdateResponse;
import com.woowacourse.edd.application.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

import static com.woowacourse.edd.presentation.controller.VideoController.VIDEO_URL;

@RestController
@RequestMapping(VIDEO_URL)
public class VideoController {

    static final String VIDEO_URL = "/v1/videos";
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> findVideo(@PathVariable long id) {
        return ResponseEntity.ok(videoService.findWithIncreaseViewCount(id));
    }

    @GetMapping
    public ResponseEntity<Page<VideoPreviewResponse>> findVideos(final Pageable pageable) {
        return ResponseEntity.ok(videoService.findByPageRequest(pageable));
    }

    @GetMapping("creators/{creatorId}")
    public ResponseEntity<List<VideoPreviewResponse>> findVideosByCreator(@PathVariable Long creatorId) {
        return ResponseEntity.ok(videoService.findByCreatorId(creatorId));
    }

    @PostMapping
    public ResponseEntity<VideoResponse> saveVideo(@RequestBody VideoSaveRequestDto requestDto, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        VideoResponse response = videoService.save(requestDto, sessionUser.getId());
        return ResponseEntity.created(URI.create(VIDEO_URL + "/" + response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateVideo(@PathVariable Long id, @RequestBody VideoUpdateRequestDto requestDto, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        VideoUpdateResponse response = videoService.update(id, requestDto, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.OK)
            .header("location", VIDEO_URL + "/" + id)
            .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVideo(@PathVariable Long id, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        videoService.delete(id, sessionUser.getId());
        return ResponseEntity.noContent().build();
    }
}
