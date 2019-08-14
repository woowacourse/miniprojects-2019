package com.woowacourse.dsgram.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepositoy extends JpaRepository<Article, Long> {
}
