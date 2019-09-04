package com.woowacourse.zzinbros.post.domain;

import com.woowacourse.zzinbros.BaseTest;
import com.woowacourse.zzinbros.post.exception.UnAuthorizedException;
import com.woowacourse.zzinbros.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.woowacourse.zzinbros.post.domain.DisplayType.ALL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PostTest extends BaseTest {
    public static final String DEFAULT_CONTENT = "some content";
    public static final String NEW_CONTENT = "newPost";
    public static final String DEFAULT_NAME = "john";
    public static final String DEFAULT_EMAIL = "john123@example.com";
    public static final String DEFAULT_PASSWORD = "123456789";
    public static final int INIT_LIKE = 0;
    public static final int INIT_SHARED = 0;

    private User defaultUser;
    private Post defaultPost;

    @BeforeEach
    void setUp() {
        defaultUser = new User(DEFAULT_NAME, DEFAULT_EMAIL, DEFAULT_PASSWORD);
        defaultPost = new Post(DEFAULT_CONTENT, defaultUser, ALL);
    }

    @Test
    void 생성자_테스트() {
        assertDoesNotThrow(() -> defaultPost);
    }

    @Test
    void 게시글_작성자가_게시글_수정_테스트() {

        assertThat((defaultPost.update(new Post(NEW_CONTENT, defaultUser, ALL))).getContents()).isEqualTo(NEW_CONTENT);
    }

    @Test
    void 게시글_작성자가_아닌_회원이_게시글_수정_테스트() {
        User user = new User("paul", "paul@example.com", "123456789");
        assertThatExceptionOfType(UnAuthorizedException.class)
                .isThrownBy(() -> defaultPost.update(new Post(NEW_CONTENT, user, ALL)));
    }

    @Test
    void 좋아요를_눌렀을_경우_테스트() {
        defaultPost.addLike();
        assertThat(defaultPost.getCountOfLike()).isEqualTo(INIT_LIKE + 1);
    }

    @Test
    void 좋아요_취소_테스트() {
        defaultPost.removeLike();
        assertThat(defaultPost.getCountOfLike()).isEqualTo(INIT_LIKE - 1);
    }

    @Test
    @DisplayName("게시글 공유횟수 증가하는지 확인")
    void sharePost() {
        defaultPost.share();
        assertThat(defaultPost.getCountOfShared()).isEqualTo(INIT_SHARED + 1);
    }
}
