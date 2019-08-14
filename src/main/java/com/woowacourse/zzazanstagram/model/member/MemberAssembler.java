package com.woowacourse.zzazanstagram.model.member;

public class MemberAssembler {
    static MemberResponse assemble(Member member) {
        return new MemberResponse(member.getNickName(), member.getName(), member.getEmail(), member.getProfileImage());
    }

    static Member toEntity(MemberRequest memberRequest) {
        return Member.MemberBuilder.aMember().email(memberRequest.getEmail())
                .name(memberRequest.getName())
                .nickName(memberRequest.getNickName())
                .password(memberRequest.getPassword())
                .profile(memberRequest.getProfile())
                .build();
    }
}
