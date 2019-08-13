package com.woowacourse.zzazanstagram.model.article.repository;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
