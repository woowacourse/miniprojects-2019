package com.woowacourse.dsgram.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepositoy extends JpaRepository<Article, Long> {
    Optional<Article> findById(Long articleId);
}
