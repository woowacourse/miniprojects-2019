package com.wootecobook.turkey.comment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByParentId(@Param("parentId") Long parentId, Pageable pageable);

    @Query(value = "SELECT c FROM Comment c WHERE c.post.id = :postId AND c.parent = null",
            countQuery = "SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId AND c.parent = null")
    Page<Comment> findAllByPostId(@Param("postId") Long postId, Pageable pageable);
}
