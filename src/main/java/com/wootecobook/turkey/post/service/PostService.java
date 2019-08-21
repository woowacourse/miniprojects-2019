package com.wootecobook.turkey.post.service;

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
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserService userService;

    @Autowired
    private UploadFileService uploadFileService;

    public PostService(final PostRepository postRepository, final UserService userService, final UploadFileService uploadFileService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.uploadFileService = uploadFileService;
    }

    public PostResponse save(PostRequest postRequest, Long userId) {
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
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findPostResponses(final Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::from);
    }

    public PostResponse update(PostRequest postRequest, Long postId, Long userId) {
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

    public void delete(Long postId, Long userId) {
        Post post = findById(postId);

        if (!post.isWrittenBy(userId)) {
            throw new NotPostOwnerException();
        }

        postRepository.delete(post);
    }
}
