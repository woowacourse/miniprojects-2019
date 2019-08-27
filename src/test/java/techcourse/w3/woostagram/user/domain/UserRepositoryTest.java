package techcourse.w3.woostagram.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void save() {
        User user = User.builder()
                .email("ab@naver.com")
                .password("Aa12345!!")
                .userContents(UserContents.builder()
                        .userName("a@naver.com")
                        .build())
                .build();

        User persistUser = testEntityManager.persist(user);
        testEntityManager.flush();
        testEntityManager.clear();

        User findUser = userRepository.findById(persistUser.getId()).get();

        assertThat(findUser).isEqualTo(user);
    }

    @Test
    public void search(){
        User user = User.builder()
                .email("ab@naver.com")
                .password("Aa12345!!")
                .userContents(UserContents.builder()
                        .userName("a@naver.com")
                        .build())
                .build();
        User persistUser = testEntityManager.persist(user);
        testEntityManager.flush();
        testEntityManager.clear();

        List<User> findUser = userRepository.findTop10ByUserContents_UserNameContainingIgnoreCaseOrderByUserContents_UserName("a");
        assertThat(findUser).contains(persistUser);

    }

    @Test
    public void searchIgnoreCase(){
        User user = User.builder()
                .email("ab@naver.com")
                .password("Aa12345!!")
                .userContents(UserContents.builder()
                        .userName("a@naver.com")
                        .build())
                .build();
        User persistUser = testEntityManager.persist(user);
        testEntityManager.flush();
        testEntityManager.clear();

        List<User> findUser = userRepository.findTop10ByUserContents_UserNameContainingIgnoreCaseOrderByUserContents_UserName("A");
        assertThat(findUser).contains(persistUser);

    }
}
