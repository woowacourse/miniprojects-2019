package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.member.exception.MemberException;
import com.woowacourse.zzazanstagram.model.member.vo.Email;
import com.woowacourse.zzazanstagram.model.member.vo.NickName;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private static final String ERROR_ILLEGAL_LOGIN_MESSAGE = "로그인 정보가 올바르지 않습니다.";
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse find(MemberLoginRequest request) {
        Member member = checkEnrolledMember(request);
        return MemberAssembler.assemble(member);
    }

    private Member checkEnrolledMember(MemberLoginRequest request) {
        return findByEmail(request.getEmail())
                .filter(m -> m.isMatchPassword(request.getPassword()))
                .orElseThrow(() -> new MemberException(ERROR_ILLEGAL_LOGIN_MESSAGE));
    }

    private Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(Email.of(email));
    }

    public void save(MemberSignUpRequest memberSignupRequest) {
        checkEnrolledEmail(memberSignupRequest.getEmail());
        checkEnrolledNickName(memberSignupRequest.getNickName());
        Member member = MemberAssembler.toEntity(memberSignupRequest);
        memberRepository.save(member);
    }

    private void checkEnrolledEmail(String email) {
        if (findByEmail(email).isPresent()) {
            throw new MemberException("이미 존재하는 이메일 입니다.");
        }
    }

    private void checkEnrolledNickName(String nickName) {
        if (findByNickName(nickName).isPresent()) {
            throw new MemberException("이미 존재하는 닉네임 입니다.");
        }
    }

    private Optional<Member> findByNickName(String nickName) {
        return memberRepository.findByNickName(NickName.of(nickName));
    }
}
