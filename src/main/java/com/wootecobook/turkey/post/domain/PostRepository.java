package com.wootecobook.turkey.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where " +
            "p.author IN (select f.relatedUserId from Friend f where f.relatingUserId = :userId)" +
            "or p.author = :userId or p.receiver = :userId")
    Page<Post> findByUserId(Pageable pageable, @Param("userId") Long userId);
}
