package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostGoodRepository extends JpaRepository<PostGood, Long> {

    Optional<PostGood> findByPostAndUser(final Post post, final User user);

    Integer countByPost(final Post post);
}
