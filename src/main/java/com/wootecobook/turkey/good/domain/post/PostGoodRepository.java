package com.wootecobook.turkey.good.domain.post;

import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostGoodRepository extends JpaRepository<PostGood, Long> {

    Optional<PostGood> findByPostAndUser(final Post post, final User user);

    int countByPost(final Post post);

    boolean existsByPostAndUser(final Post post, final User user);
}
