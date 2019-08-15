package com.woowacourse.zzazanstagram.model.member.service;

import com.woowacourse.zzazanstagram.model.member.domain.Member;
import com.woowacourse.zzazanstagram.model.member.domain.vo.Email;
import com.woowacourse.zzazanstagram.model.member.domain.vo.NickName;
import com.woowacourse.zzazanstagram.model.member.dto.MemberSignUpRequest;
import com.woowacourse.zzazanstagram.model.member.exception.MemberException;
import com.woowacourse.zzazanstagram.model.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findMemberByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new MemberException("잘못된 접근입니다."));
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
