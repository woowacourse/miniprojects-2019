package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.CommentRequestDto;
import com.woowacourse.edd.domain.Comment;
import com.woowacourse.edd.exceptions.CommentNotFoundException;
import com.woowacourse.edd.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentInternalService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentInternalService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> retrieve(Long videoId) {
        return commentRepository.findCommentsByVideo_Id(videoId);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    public Comment update(Long commentId, Long userId, Long videoId, CommentRequestDto commentRequestDto) {
        Comment comment = findById(commentId);
        comment.update(commentRequestDto.getContents(), userId, videoId);
        return comment;
    }

    public void delete(Long commentId, Long userId, Long videoId) {
        Comment comment = findById(commentId);
        comment.checkControllable(userId, videoId);
        commentRepository.deleteById(commentId);
    }
}
