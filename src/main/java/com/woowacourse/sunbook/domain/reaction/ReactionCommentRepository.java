package com.woowacourse.sunbook.domain.reaction;

import com.woowacourse.sunbook.domain.comment.Comment;
import com.woowacourse.sunbook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReactionCommentRepository extends JpaRepository<ReactionComment, Long> {
    Optional<ReactionComment> findByAuthorAndComment(User author, Comment comment);

    List<ReactionComment> findAllByComment(Comment comment);
}
