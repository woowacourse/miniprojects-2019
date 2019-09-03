package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.CommentConverter;
import com.woowacourse.edd.application.dto.CommentRequestDto;
import com.woowacourse.edd.application.response.CommentResponse;
import com.woowacourse.edd.domain.Comment;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.domain.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    private final VideoInternalService videoInternalService;
    private final CommentInternalService commentInternalService;
    private final UserInternalService userInternalService;

    @Autowired
    public CommentService(VideoInternalService videoInternalService, CommentInternalService commentInternalService, UserInternalService userInternalService) {
        this.videoInternalService = videoInternalService;
        this.commentInternalService = commentInternalService;
        this.userInternalService = userInternalService;
    }

    public CommentResponse save(Long videoId, Long authorId, CommentRequestDto commentRequestDto) {
        Video video = videoInternalService.findById(videoId);
        User author = userInternalService.findById(authorId);
        Comment comment = commentInternalService.save(CommentConverter.toEntity(video, author, commentRequestDto));
        return CommentConverter.toResponse(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> retrieve(Long videoId) {
        List<Comment> comments = commentInternalService.retrieve(videoId);
        return comments.stream()
            .map(CommentConverter::toResponse)
            .collect(Collectors.toList());
    }

    public CommentResponse update(Long commentId, Long userId, Long videoId, CommentRequestDto commentRequestDto) {
        Comment comment = commentInternalService.update(commentId, userId, videoId, CommentConverter.escapeUpdateRequestDto(commentRequestDto));
        return CommentConverter.toResponse(comment);
    }

    public void delete(Long commentId, Long userId, Long videoId) {
        commentInternalService.delete(commentId, userId, videoId);
    }
}
