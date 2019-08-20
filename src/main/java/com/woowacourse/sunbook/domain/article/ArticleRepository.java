package com.woowacourse.sunbook.domain.article;

import com.woowacourse.sunbook.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByAuthor(Pageable pageable, User author);
}
