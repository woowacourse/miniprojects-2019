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
        Member member = validateEnrolledMember(request);
        return MemberAssembler.assemble(member);
    }

    private Member validateEnrolledMember(MemberLoginRequest request) {
        return memberRepository.findByEmail(Email.of(request.getEmail()))
                .filter(m -> m.isMatchPassword(request.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보가 올바르지 않습니다."));
    }

    public void save(MemberSignUpRequest memberSignupRequest) {
        Member member = MemberAssembler.toEntity(memberSignupRequest);
        memberRepository.save(member);
    }
}
