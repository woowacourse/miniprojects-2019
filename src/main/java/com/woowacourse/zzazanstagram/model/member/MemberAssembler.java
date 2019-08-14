package com.woowacourse.zzazanstagram.model.member;

public class MemberAssembler {
    static MemberResponse assemble(Member member) {
        return new MemberResponse(member.nickName(), member.name(), member.email(), member.profileImage());
    }

    static Member toEntity(MemberSignUpRequest memberSignupRequest) {
        return Member.MemberBuilder.aMember().email(memberSignupRequest.getEmail())
                .name(memberSignupRequest.getName())
                .nickName(memberSignupRequest.getNickName())
                .password(memberSignupRequest.getPassword())
                .profile(memberSignupRequest.getProfile())
                .build();
    }
}
