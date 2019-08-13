package com.woowacourse.zzazanstagram.model.member;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse find(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user가 없습니다"));
        return MemberAssembler.assemble(member);
    }

    public void save(MemberRequest memberRequest) {
        Member member = Member.MemberBuilder.aMember().email(memberRequest.getEmail())
                .name(memberRequest.getName())
                .nickName(memberRequest.getNickName())
                .password(memberRequest.getPassword())
                .profile(memberRequest.getProfile())
                .build();
        memberRepository.save(member);
    }
}
