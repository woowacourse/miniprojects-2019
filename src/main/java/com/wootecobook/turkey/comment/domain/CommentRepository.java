package com.wootecobook.turkey.comment.domain;

import com.wootecobook.turkey.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //TODO 답글만 조회

    //TODO 답글 제외하고 조회
    Page<Comment> findAllByPost(Post post, Pageable pageable);
}
