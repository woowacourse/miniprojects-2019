package techcourse.w3.woostagram.follow.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import techcourse.w3.woostagram.user.domain.User;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findAllByTo(User to);
}
