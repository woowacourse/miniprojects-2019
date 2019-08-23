package com.wootecobook.turkey.comment.domain;

import com.wootecobook.turkey.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByParentId(Long parentId, Pageable pageable);

    Page<Comment> findAllByPostIdAndParentIdIsNull(Long postId, Pageable pageable);

    int countByPost(Post post);
}
