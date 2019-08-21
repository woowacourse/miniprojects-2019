package com.woowacourse.zzazanstagram;

import com.woowacourse.zzazanstagram.model.follow.dto.FollowRequest;
import com.woowacourse.zzazanstagram.model.follow.service.FollowService;
import com.woowacourse.zzazanstagram.model.member.dto.MemberSignUpRequest;
import com.woowacourse.zzazanstagram.model.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {
    @Autowired
    private MemberService memberService;

    @Autowired
    private FollowService followService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        MemberSignUpRequest request1 = new MemberSignUpRequest();
        MemberSignUpRequest request2 = new MemberSignUpRequest();
        MemberSignUpRequest request3 = new MemberSignUpRequest();
        MemberSignUpRequest request4 = new MemberSignUpRequest();

        request1.setName("first");
        request1.setNickName("firstNick");
        request1.setProfile("https://image.shutterstock.com/image-photo/white-transparent-leaf-on-mirror-600w-1029171697.jpg");
        request1.setEmail("root1@gmail.com");
        request1.setPassword("Pp123!@#");

        request2.setName("second");
        request2.setNickName("secondNick");
        request2.setProfile("https://image.shutterstock.com/image-photo/white-transparent-leaf-on-mirror-600w-1029171697.jpg");
        request2.setEmail("root2@gmail.com");
        request2.setPassword("Pp123!@#");

        request3.setName("third");
        request3.setNickName("thirdNick");
        request3.setProfile("https://image.shutterstock.com/image-photo/white-transparent-leaf-on-mirror-600w-1029171697.jpg");
        request3.setEmail("root3@gmail.com");
        request3.setPassword("Pp123!@#");

        request4.setName("fourth");
        request4.setNickName("fourthNick");
        request4.setProfile("https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-600w-1048185397.jpg");
        request4.setEmail("root4@gmail.com");
        request4.setPassword("Pp123!@#");

        memberService.save(request1);
        memberService.save(request2);
        memberService.save(request3);
        memberService.save(request4);

        FollowRequest followRequest = new FollowRequest();
        followRequest.setFolloweeId(1L);
        followRequest.setFollowerId(2L);

        FollowRequest followRequest2 = new FollowRequest();
        followRequest2.setFolloweeId(1L);
        followRequest2.setFollowerId(3L);

        FollowRequest followRequset3 = new FollowRequest();
        followRequset3.setFolloweeId(4L);
        followRequset3.setFollowerId(1L);

        FollowRequest followRequset4 = new FollowRequest();
        followRequset4.setFolloweeId(3L);
        followRequset4.setFollowerId(1L);

        followService.save(followRequest);
        followService.save(followRequest2);
        followService.save(followRequset3);
        followService.save(followRequset4);
    }
}
