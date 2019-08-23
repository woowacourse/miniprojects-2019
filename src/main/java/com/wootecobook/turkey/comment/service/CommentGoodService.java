package com.wootecobook.turkey.comment.service;

import com.wootecobook.turkey.comment.domain.Comment;
import com.wootecobook.turkey.comment.domain.CommentGood;
import com.wootecobook.turkey.comment.domain.CommentGoodRepository;
import com.wootecobook.turkey.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentGoodService {

    private final CommentGoodRepository commentGoodRepository;

    public CommentGoodService(final CommentGoodRepository commentGoodRepository) {
        this.commentGoodRepository = commentGoodRepository;
    }

    List<CommentGood> good(final Comment comment, final User user) {
        Optional<CommentGood> maybeCommentGood = commentGoodRepository.findByCommentAndUser(comment, user);

        if (maybeCommentGood.isPresent()) {
            cancelGoodRequest(maybeCommentGood.get());
        } else {
            approveGoodRequest(new CommentGood(user, comment));
        }

        return findByComment(comment);
    }

    private void approveGoodRequest(CommentGood commentGood) {
        commentGoodRepository.save(commentGood);
    }

    private void cancelGoodRequest(CommentGood commentGood) {
        commentGoodRepository.delete(commentGood);
    }

    List<CommentGood> findByComment(Comment comment) {
        return commentGoodRepository.findByComment(comment);
    }
}
