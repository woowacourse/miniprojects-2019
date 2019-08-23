package com.woowacourse.sunbook.domain.article;

import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ArticleTest {
    private static final String CONTENTS = "SunBook Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";
    private static final String EMPTY = "";

    private static final String UPDATE_CONTENTS = "Update Contents";
    private static final String UPDATE_IMAGE_URL = "http://mblogthumb2.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800";
    private static final String UPDATE_VIDEO_URL = "https://youtu.be/4HG_CJzyX6A";

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(new UserEmail("ddu0422@naver.com"), new UserPassword("asdf1234!A"), new UserName("미르"));
    }

    static Stream<Arguments> articleParameters() {
        return Stream.of(
                Arguments.of(CONTENTS, IMAGE_URL, VIDEO_URL),
                Arguments.of(EMPTY, IMAGE_URL, VIDEO_URL),
                Arguments.of(CONTENTS, EMPTY, VIDEO_URL),
                Arguments.of(CONTENTS, IMAGE_URL, EMPTY),
                Arguments.of(CONTENTS, EMPTY, EMPTY),
                Arguments.of(EMPTY, IMAGE_URL, EMPTY),
                Arguments.of(EMPTY, EMPTY, VIDEO_URL)
        );
    }

    @ParameterizedTest
    @MethodSource("articleParameters")
    void 게시글_정상_생성_테스트_통합(String contents, String imageUrl, String videoUrl) {
        assertDoesNotThrow(() -> new Article(new ArticleFeature(contents, imageUrl, videoUrl), user));
    }

    @Test
    void 게시글_생성_오류_아무것도_없음() {
        assertThrows(IllegalArgumentException.class, () -> new Article(new ArticleFeature(EMPTY, EMPTY, EMPTY), user));
    }

    @Test
    void 게시글_정상_수정() {
        Article article = new Article(new ArticleFeature(CONTENTS, IMAGE_URL, VIDEO_URL), user);
        ArticleFeature updatedArticleFeature = new ArticleFeature(UPDATE_CONTENTS, UPDATE_IMAGE_URL, UPDATE_VIDEO_URL);
        assertDoesNotThrow(() -> article.update(updatedArticleFeature));
    }

    @Test
    void 게시글_유저_확인() {
        assertThat(new Article(new ArticleFeature(CONTENTS, IMAGE_URL, VIDEO_URL), user).getAuthor()).isEqualTo(user);
    }

    @Test
    void 같은유저인지_확인() {
        User author = mock(User.class);
        given(author.getId()).willReturn(1L);

        ArticleFeature articleFeature = new ArticleFeature(CONTENTS, IMAGE_URL, VIDEO_URL);
        Article article = new Article(articleFeature, author);

        assertTrue(article.isSameUser(author));
    }
}