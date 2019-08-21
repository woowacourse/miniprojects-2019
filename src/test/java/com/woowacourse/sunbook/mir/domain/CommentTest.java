package com.woowacourse.sunbook.mir.domain;

import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.mir.domain.exception.MismatchAuthException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
