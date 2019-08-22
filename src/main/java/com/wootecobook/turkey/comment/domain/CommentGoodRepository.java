package com.wootecobook.turkey.comment.domain;

import com.wootecobook.turkey.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentGoodRepository extends JpaRepository<CommentGood, Long> {

    Optional<CommentGood> findByCommentAndUser(final Comment comment, final User user);

    Integer countByComment(final Comment comment);
}
