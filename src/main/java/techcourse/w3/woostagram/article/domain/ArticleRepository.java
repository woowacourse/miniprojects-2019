package techcourse.w3.woostagram.article.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import techcourse.w3.woostagram.user.domain.User;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByUserIn(List<User> Users, Pageable pageable);
    Page<Article> findByUserNotIn(List<User> Users, Pageable pageable);
}
