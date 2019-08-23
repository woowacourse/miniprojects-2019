package techcourse.w3.woostagram.like.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.user.domain.User;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllByArticle(Article article);

    Likes findByArticleAndUser_Id(Article article, Long userId);

    Page<Likes> findAllByUser(User user, Pageable pageable);
}
