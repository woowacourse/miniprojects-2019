package com.woowacourse.sunbook.domain.reaction;

import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.comment.Comment;
import com.woowacourse.sunbook.domain.comment.CommentFeature;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReactionCommentTest {
    private final UserEmail userEmail = new UserEmail("user@naver.com");
    private final UserName userName = new UserName("park");
    private final UserPassword userPassword = new UserPassword("Password123!");
    private final User author = new User(userEmail, userPassword, userName);

    private static final String ARTICLE_CONTENTS = "SunBook Article Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";
    private final Article article = new Article(new ArticleFeature(ARTICLE_CONTENTS, IMAGE_URL, VIDEO_URL), author);

    private static final String COMMENT_CONTENTS = "SunBook Comment Contents";
    private final CommentFeature commentFeature = new CommentFeature(COMMENT_CONTENTS);
    private final Comment comment = new Comment(commentFeature, author, article);
    
    @Test
    void 좋아요_정상_생성() {
        assertDoesNotThrow(() -> new ReactionComment(author, comment));
    }

    @Test
    void 좋아요_한번_정상_누르기() {
        ReactionComment reactionComment = new ReactionComment(author, comment);
        reactionComment.toggleGood();
        assertTrue(reactionComment.getHasGood());
    }

    @Test
    void 좋아요_한번_정상_취소() {
        ReactionComment reactionComment = new ReactionComment(author, comment);
        reactionComment.toggleGood();
        reactionComment.toggleGood();
        assertFalse(reactionComment.getHasGood());
    }
}
