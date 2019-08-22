package com.woowacourse.sunbook.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.BitSet;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
}
