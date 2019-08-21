package com.woowacourse.sunbook.domain.reaction;

import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactionArticleRepository extends JpaRepository<ReactionArticle, Long> {
    boolean existsByAuthorAndArticle(User author, Article article);

    ReactionArticle findByAuthorAndArticle(User author, Article article);

    List<ReactionArticle> findAllByArticle(Article article);
}
