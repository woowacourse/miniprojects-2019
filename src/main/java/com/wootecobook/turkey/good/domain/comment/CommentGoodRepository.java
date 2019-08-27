package com.wootecobook.turkey.good.domain.comment;

import com.wootecobook.turkey.comment.domain.Comment;
import com.wootecobook.turkey.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentGoodRepository extends JpaRepository<CommentGood, Long> {

    Optional<CommentGood> findByCommentAndUser(final Comment comment, final User user);

    int countByComment(final Comment comment);

    boolean existsByCommentAndUser(final Comment comment, final User user);
}
