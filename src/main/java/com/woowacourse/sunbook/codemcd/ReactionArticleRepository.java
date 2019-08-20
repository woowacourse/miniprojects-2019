package com.woowacourse.sunbook.codemcd;

import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionArticleRepository extends JpaRepository<ReactionArticle, Long> {
    Long countByArticle(Article article);

    boolean existsByArticle(Article article);

    void deleteByAuthorAndArticle(User author, Article article);
}
