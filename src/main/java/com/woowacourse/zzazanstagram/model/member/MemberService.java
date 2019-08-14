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
                .filter(m -> m.isMatchPassword(request.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보가 올바르지 않습니다."));
        return MemberAssembler.assemble(member);
    }

    public void save(MemberSignUpRequest memberSignupRequest) {
        Member member = MemberAssembler.toEntity(memberSignupRequest);
        memberRepository.save(member);
    }
}
