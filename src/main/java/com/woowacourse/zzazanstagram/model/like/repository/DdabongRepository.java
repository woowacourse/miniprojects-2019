package com.woowacourse.zzazanstagram.model.like.repository;

import com.woowacourse.zzazanstagram.model.like.domain.Ddabong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DdabongRepository extends JpaRepository<Ddabong, Long> {
}