package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.commons.GoodResponse;
import com.wootecobook.turkey.file.domain.UploadFile;
import com.wootecobook.turkey.file.service.UploadFileService;
import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostRepository;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import com.wootecobook.turkey.post.service.exception.NotPostOwnerException;
import com.wootecobook.turkey.user.domain.User;
import com.wootecobook.turkey.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private static final String NOT_FOUND_MESSAGE = "존재하지 않는 게시글입니다.";

    private final PostRepository postRepository;
    private final PostGoodService postGoodService;
    private final UserService userService;
    private final UploadFileService uploadFileService;

    public PostService(final PostRepository postRepository, final PostGoodService postGoodService,
                       final UserService userService, final UploadFileService uploadFileService) {
        this.postRepository = postRepository;
        this.postGoodService = postGoodService;
        this.userService = userService;
        this.uploadFileService = uploadFileService;
    }

    public PostResponse save(final PostRequest postRequest, final Long userId) {
        User user = userService.findById(userId);
        List<UploadFile> savedFiles = saveAttachments(postRequest.getFiles(), user);

        Post savedPost = postRepository.save(postRequest.toEntity(user, savedFiles));

        return PostResponse.from(savedPost);
    }

    private List<UploadFile> saveAttachments(List<MultipartFile> attachments, User owner) {
        if (attachments == null) {
            return new ArrayList<>();
        }
        return attachments.stream()
                .map(file -> uploadFileService.save(file, "dir", owner))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Post findById(final Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findPostResponses(final Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::from);
    }

    public PostResponse update(final PostRequest postRequest, final Long postId, final Long userId) {
        Post post = findById(postId);

        if (post.isWrittenBy(userId)) {
            Post updatePost = Post.builder()
                    .id(postId)
                    .contents(new Contents(postRequest.getContents()))
                    .build();

            post.update(updatePost);
            return PostResponse.from(post);
        }

        throw new NotPostOwnerException();
    }

    public void delete(final Long postId, final Long userId) {
        Post post = findById(postId);

        if (!post.isWrittenBy(userId)) {
            throw new NotPostOwnerException();
        }

        postRepository.delete(post);
    }

    public GoodResponse good(final Long postId, final Long userId) {
        Post post = findById(postId);
        User user = userService.findById(userId);

        return new GoodResponse(postGoodService.good(post, user));
    }

    public GoodResponse countPostGoodByPost(Long postId) {
        Post post = findById(postId);

        return new GoodResponse(postGoodService.countByPost(post));
    }
}
