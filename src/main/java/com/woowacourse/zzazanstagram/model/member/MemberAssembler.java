package com.woowacourse.zzazanstagram.model.member;

public class MemberAssembler {
    static MemberResponse assemble(Member member) {
        return new MemberResponse(member.getNickName(), member.getName(), member.getEmail(), member.getProfile());
    }
}
