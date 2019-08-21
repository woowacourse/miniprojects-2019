package com.woowacourse.sunbook.domain.comment;

import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.comment.exception.MismatchAuthException;
import com.woowacourse.sunbook.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
class CommentTest {

    @Spy
    @InjectMocks
    private Comment comment;

    @Mock
    private User writer;

    @Mock
    private User other;

    @Mock
    private Article article;

    @Mock
    private Article otherArticle;

    @Mock
    private CommentFeature commentFeature;

    @Test
    void 댓글_생성() {
        assertDoesNotThrow(() -> new Comment(commentFeature, writer, article));
    }

    @Test
    void 권한_있는_사용자_댓글_수정() {
        doNothing().when(comment).validateAuth(other, article);

        assertDoesNotThrow(() -> comment.modify(commentFeature, other, article));
    }

    @Test
    void 권한_없는_사용자_댓글_수정() {
        doThrow(MismatchAuthException.class).when(comment).validateAuth(other, article);

        assertThrows(MismatchAuthException.class, () -> {
            comment.modify(commentFeature, other, article);
        });
    }

    @Test
    void 다른_게시글_댓글_수정() {
        doThrow(MismatchAuthException.class).when(comment).validateAuth(writer, otherArticle);

        assertThrows(MismatchAuthException.class, () -> {
            comment.modify(commentFeature, writer, otherArticle);
        });
    }
}
