package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.MockStorage;
import com.woowacourse.sunbook.domain.reaction.ReactionComment;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ReactionCommentServiceTest extends MockStorage {
    private static final Long AUTHOR_ID = 1L;
    private static final Long COMMENT_ID = 1L;

    @InjectMocks
    private ReactionCommentService injectReactionCommentService;

    @Test
    void 좋아요_처음_누르기_정상() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(commentService.findById(COMMENT_ID)).willReturn(comment);
        given(reactionCommentRepository.findByAuthorAndComment(author, comment))
                .willReturn(Optional.of(reactionComment));

        injectReactionCommentService.clickGood(AUTHOR_ID, COMMENT_ID);

        verify(reactionCommentRepository).save(any(ReactionComment.class));
    }

    @Test
    void 좋아요_취소_정상() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(commentService.findById(COMMENT_ID)).willReturn(comment);
        given(reactionCommentRepository.findByAuthorAndComment(author, comment))
                .willReturn(Optional.of(reactionComment));

        injectReactionCommentService.clickGood(AUTHOR_ID, COMMENT_ID);

        verify(reactionCommentRepository, never()).save(any(ReactionComment.class));
    }

    @Test
    void 좋아요가_눌린_게시글_정상_조회() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(commentService.findById(COMMENT_ID)).willReturn(comment);
        given(reactionCommentRepository.findByAuthorAndComment(author, comment))
                .willReturn(Optional.of(reactionComment));

        injectReactionCommentService.showCount(AUTHOR_ID, COMMENT_ID);

        verify(reactionCommentRepository).findAllByComment(comment);
    }

    @Test
    void 좋아요가_눌리지_않은_게시글_정상_조회() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(commentService.findById(COMMENT_ID)).willReturn(comment);

        injectReactionCommentService.showCount(AUTHOR_ID, COMMENT_ID);

        verify(reactionCommentRepository).findAllByComment(comment);
    }
}
