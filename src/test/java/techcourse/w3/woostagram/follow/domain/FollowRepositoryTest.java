package techcourse.w3.woostagram.follow.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import techcourse.w3.woostagram.follow.exception.FollowNotFoundException;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FollowRepositoryTest {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User persistUser1;
    private User persistUser2;
    private User persistUser3;

    @BeforeEach
    void setUp() {
        User user1 = User.builder()
                .email("ab@naver.com")
                .password("Aa12345!!")
                .userContents(UserContents.builder()
                        .userName("user1")
                        .build())
                .build();

        User user2 = User.builder()
                .email("abc@naver.com")
                .password("Aa12345!!")
                .userContents(UserContents.builder()
                        .userName("user2")
                        .build())
                .build();

        User user3 = User.builder()
                .email("abcd@naver.com")
                .password("Aa12345!!")
                .userContents(UserContents.builder()
                        .userName("user3")
                        .build())
                .build();

        persistUser1 = testEntityManager.persist(user1);
        persistUser2 = testEntityManager.persist(user2);
        persistUser3 = testEntityManager.persist(user3);
    }

    @Test
    void findAllByFrom_correct_ok() {
        Follow follow1 = Follow.builder()
                .from(persistUser1)
                .to(persistUser2).build();

        Follow follow2 = Follow.builder()
                .from(persistUser1)
                .to(persistUser3).build();

        testEntityManager.persist(follow1);
        testEntityManager.persist(follow2);

        List<Follow> result = followRepository.findAllByFrom(persistUser1);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findAllByTo_correct_ok() {
        Follow follow1 = Follow.builder()
                .from(persistUser2)
                .to(persistUser1).build();

        Follow follow2 = Follow.builder()
                .from(persistUser3)
                .to(persistUser1).build();

        testEntityManager.persist(follow1);
        testEntityManager.persist(follow2);

        List<Follow> result = followRepository.findAllByTo(persistUser1);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findByFromAndTo_correct_ok() {
        Follow follow1 = Follow.builder()
                .from(persistUser1)
                .to(persistUser2).build();

        testEntityManager.persist(follow1);

        Follow followResult = followRepository.findByFromAndTo(persistUser1, persistUser2).orElseThrow(FollowNotFoundException::new);

        assertThat(followResult).isEqualTo(follow1);
    }
}
