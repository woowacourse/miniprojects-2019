package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.reaction.ReactionDto;
import com.woowacourse.sunbook.domain.comment.Comment;
import com.woowacourse.sunbook.domain.reaction.ReactionComment;
import com.woowacourse.sunbook.domain.reaction.ReactionCommentRepository;
import com.woowacourse.sunbook.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReactionCommentService {
    private final ReactionCommentRepository reactionCommentRepository;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public ReactionCommentService(final ReactionCommentRepository reactionCommentRepository,
                                  final CommentService commentService,
                                  final UserService userService) {
        this.reactionCommentRepository = reactionCommentRepository;
        this.commentService = commentService;
        this.userService = userService;
    }

    @Transactional
    public ReactionDto clickGood(final Long userId, final Long commentId) {
        User author = userService.findById(userId);
        Comment comment = commentService.findById(commentId);

        ReactionComment reactionComment = reactionCommentRepository
                .findByAuthorAndComment(author, comment)
                .orElse(reactionCommentRepository.save(new ReactionComment(author, comment)));
        reactionComment.toggleGood();

        return new ReactionDto(getCount(comment), reactionComment.getHasGood());
    }

    public ReactionDto showCount(final Long userId, final Long commentId) {
        User author = userService.findById(userId);
        Comment comment = commentService.findById(commentId);

        return new ReactionDto(getCount(comment),
                isClickedGoodInCommentByLoginUser(author, comment));
    }

    private Long getCount(final Comment comment) {
        return reactionCommentRepository.findAllByComment(comment)
                .stream()
                .filter(ReactionComment::getHasGood)
                .count()
                ;
    }

    private boolean isClickedGoodInCommentByLoginUser(final User loginUser, final Comment comment) {
        return reactionCommentRepository
                .findByAuthorAndComment(loginUser, comment)
                .map(ReactionComment::getHasGood)
                .orElse(false)
                ;
    }
}
