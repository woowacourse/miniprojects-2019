package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.member.vo.Email;
import com.woowacourse.zzazanstagram.model.member.vo.NickName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(Email email);

    Optional<Member> findByNickName(NickName nickName);
}
