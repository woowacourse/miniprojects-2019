package techcourse.w3.woostagram.follow.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import techcourse.w3.woostagram.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findAllByTo(User to);
    Optional<Follow> findByFromAndTo(User from, User to);
    List<Follow> findAllByFrom(User from);
}
