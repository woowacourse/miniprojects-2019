package com.woowacourse.zzazanstagram.model.ddabong.repository;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.ddabong.domain.Ddabong;
import com.woowacourse.zzazanstagram.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DdabongRepository extends JpaRepository<Ddabong, Long> {
    Ddabong findByArticleAndMember(Article article, Member member);
}