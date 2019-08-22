package com.wootecobook.turkey.post.controller.api;

import com.wootecobook.turkey.commons.GoodResponse;
import com.wootecobook.turkey.commons.resolver.UserSession;
import com.wootecobook.turkey.post.service.PostService;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;

    public PostApiController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid PostRequest postRequest, UserSession userSession) {

        PostResponse postResponse = postService.save(postRequest, userSession.getId());
        final URI uri = linkTo(PostApiController.class).toUri();
        return ResponseEntity.created(uri).body(postResponse);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> list(@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponse> postResponses = postService.findPostResponses(pageable);
        return ResponseEntity.ok(postResponses);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> update(@PathVariable Long postId, @RequestBody @Valid PostRequest postRequest,
                                               UserSession userSession) {

        PostResponse postResponse = postService.update(postRequest, postId, userSession.getId());

        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity delete(@PathVariable Long postId, UserSession userSession) {
        postService.delete(postId, userSession.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}/good")
    public ResponseEntity good(@PathVariable Long postId, UserSession userSession) {
        GoodResponse goodResponse = postService.good(postId, userSession.getId());
        return ResponseEntity.ok(goodResponse);
    }

    @GetMapping("/{postId}/good/count")
    public ResponseEntity countGood(@PathVariable Long postId) {
        GoodResponse goodResponse = postService.countPostGoodByPost(postId);
        return ResponseEntity.ok(goodResponse);
    }
}
