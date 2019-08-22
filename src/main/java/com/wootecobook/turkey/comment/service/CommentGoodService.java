package com.wootecobook.turkey.comment.service;

import com.wootecobook.turkey.comment.domain.Comment;
import com.wootecobook.turkey.comment.domain.CommentGood;
import com.wootecobook.turkey.comment.domain.CommentGoodRepository;
import com.wootecobook.turkey.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentGoodService {

    private final CommentGoodRepository commentGoodRepository;

    public CommentGoodService(final CommentGoodRepository commentGoodRepository) {
        this.commentGoodRepository = commentGoodRepository;
    }

    Integer good(final Comment comment, final User user) {
        Optional<CommentGood> maybeCommentGood = commentGoodRepository.findByCommentAndUser(comment, user);

        if (maybeCommentGood.isPresent()) {
            commentGoodRepository.delete(maybeCommentGood.get());
        } else {
            commentGoodRepository.save(new CommentGood(user, comment));
        }

        return countByComment(comment);
    }

    Integer countByComment(Comment comment) {
        return commentGoodRepository.countByComment(comment);
    }
}
