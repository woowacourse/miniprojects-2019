package com.woowacourse.edd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EddApplicationTests {

    public static final long DEFAULT_VIDEO_ID = 1;
    public static final String DEFAULT_VIDEO_TITLE = "로비가 좋아하는 노래";
    public static final String DEFAULT_VIDEO_CONTENTS = "로비만 좋아하는 노래";
    public static final String DEFAULT_VIDEO_YOUTUBEID = "S8e1geEpnTA";


    @Test
    public void contextLoads() {

    }

}
