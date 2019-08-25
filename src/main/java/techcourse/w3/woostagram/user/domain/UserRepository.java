package techcourse.w3.woostagram.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserContents_UserName(String userName);
}
