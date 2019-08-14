package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.member.vo.Email;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse find(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(Email.of(request.getEmail()))
                .orElseThrow(() -> new IllegalArgumentException("user가 없습니다"));
        return MemberAssembler.assemble(member);
    }

    public void save(MemberSignUpRequest memberSignupRequest) {
        Member member = MemberAssembler.toEntity(memberSignupRequest);
        memberRepository.save(member);
    }
}
