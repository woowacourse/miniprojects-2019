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
        Member member = MemberAssembler.toEntity(memberRequest);
        memberRepository.save(member);
    }
}
