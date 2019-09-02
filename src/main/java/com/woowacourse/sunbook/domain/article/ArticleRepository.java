package com.woowacourse.sunbook.domain.article;

import com.woowacourse.sunbook.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByAuthor(Pageable pageable, User author);

    List<Article> findAllByAuthor(User author);

    List<Article> findAllByAuthorAndOpenRange(User author, OpenRange openRange);

    List<Article> findAllByAuthorInAndOpenRange(List<User> authors, OpenRange openRange);

    List<Article> findAllByOpenRangeAndAuthorNot(OpenRange openRange, User author);
}
