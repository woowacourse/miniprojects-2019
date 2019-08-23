package com.wootecobook.turkey.comment.service;

import com.wootecobook.turkey.comment.domain.Comment;
import com.wootecobook.turkey.comment.domain.CommentGood;
import com.wootecobook.turkey.comment.domain.CommentGoodRepository;
import com.wootecobook.turkey.post.service.GoodService;
import com.wootecobook.turkey.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentGoodService implements GoodService<CommentGood, Comment> {

    private final CommentGoodRepository commentGoodRepository;

    public CommentGoodService(final CommentGoodRepository commentGoodRepository) {
        this.commentGoodRepository = commentGoodRepository;
    }

    @Override
    public List<CommentGood> toggleGood(final Comment comment, final User user) {
        Optional<CommentGood> maybeCommentGood = commentGoodRepository.findByCommentAndUser(comment, user);

        if (maybeCommentGood.isPresent()) {
            cancelGoodRequest(maybeCommentGood.get());
        } else {
            approveGoodRequest(new CommentGood(user, comment));
        }

        return findBy(comment);
    }

    private void approveGoodRequest(CommentGood commentGood) {
        commentGoodRepository.save(commentGood);
    }

    private void cancelGoodRequest(CommentGood commentGood) {
        commentGoodRepository.delete(commentGood);
    }

    @Override
    public List<CommentGood> findBy(Comment comment) {
        return commentGoodRepository.findByComment(comment);
    }
}
