package com.woowacourse.sunbook.domain.reaction;

import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionArticleRepository extends JpaRepository<ReactionArticle, Long> {
    Long countByArticle(Article article);

    void deleteByAuthorAndArticle(User author, Article article);

    boolean existsByAuthorAndArticle(User author, Article article);
}
