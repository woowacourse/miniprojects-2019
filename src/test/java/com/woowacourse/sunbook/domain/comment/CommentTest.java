package com.woowacourse.sunbook.domain.comment;

import com.woowacourse.sunbook.MockStorage;
import com.woowacourse.sunbook.domain.comment.exception.MismatchAuthException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

class CommentTest extends MockStorage {

    @Spy
    @InjectMocks
    private Comment injectComment;

    @Test
    void 댓글_생성() {
        assertDoesNotThrow(() -> new Comment(commentFeature, writer, article, null));
    }

    @Test
    void 권한_있는_사용자_댓글_수정() {
        doNothing().when(injectComment).validateAuth(other, article);

        assertDoesNotThrow(() -> injectComment.modify(commentFeature, other, article));
    }

    @Test
    void 권한_없는_사용자_댓글_수정() {
        doThrow(MismatchAuthException.class).when(injectComment).validateAuth(other, article);

        assertThrows(MismatchAuthException.class, () -> {
            injectComment.modify(commentFeature, other, article);
        });
    }

    @Test
    void 다른_게시글_댓글_수정() {
        doThrow(MismatchAuthException.class).when(injectComment).validateAuth(writer, otherArticle);

        assertThrows(MismatchAuthException.class, () -> {
            injectComment.modify(commentFeature, writer, otherArticle);
        });
    }
}
