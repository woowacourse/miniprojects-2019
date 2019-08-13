package com.wootecobook.turkey.post.controller;

import com.wootecobook.turkey.commons.resolver.UserSession;
import com.wootecobook.turkey.post.service.PostService;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;

    public PostApiController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.save(postRequest, null);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> list(@PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponse> postResponses = postService.findPostResponses(pageable);
        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }
}
