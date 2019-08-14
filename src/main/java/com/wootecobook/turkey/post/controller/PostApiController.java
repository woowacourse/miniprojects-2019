package com.wootecobook.turkey.post.controller;

import com.wootecobook.turkey.post.controller.exception.PostBadRequestException;
import com.wootecobook.turkey.post.service.PostService;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;

    public PostApiController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody @Valid PostRequest postRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new PostBadRequestException();
        }

        PostResponse postResponse = postService.save(postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> list(@PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponse> postResponses = postService.findPostResponses(pageable);
        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> update(@PathVariable Long postId, @RequestBody @Valid PostRequest postRequest,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new PostBadRequestException();
        }

        PostResponse postResponse = postService.update(postRequest, postId);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity delete(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok().build();
    }
}
