package com.woowacourse.edd.repository;

import com.woowacourse.edd.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByVideo_Id(Long videoId);
}
