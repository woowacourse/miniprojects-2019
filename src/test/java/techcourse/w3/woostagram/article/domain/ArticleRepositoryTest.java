package techcourse.w3.woostagram.article.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findByUserIn_correct_ok() {
        User user1 = User.builder()
                .email("ab@naver.com")
                .password("Aa12345!!")
                .userContents(UserContents.builder()
                        .userName("a@naver.com")
                        .build())
                .build();

        User user2 = User.builder()
                .email("abc@naver.com")
                .password("Aa12345!!")
                .userContents(UserContents.builder()
                        .userName("ac@naver.com")
                        .build())
                .build();

        User persistUser1 = testEntityManager.persist(user1);
        User persistUser2 = testEntityManager.persist(user2);

        Article article1 = Article.builder()
                .contents("hihi")
                .imageUrl("moomin")
                .user(persistUser1)
                .build();

        Article article2 = Article.builder()
                .contents("hihi")
                .imageUrl("moomin")
                .user(persistUser2)
                .build();

        testEntityManager.persist(article1);
        testEntityManager.persist(article2);

        Page<Article> result = articleRepository.findByUserIn(Arrays.asList(persistUser1, persistUser2), new PageRequest(0,2));
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }
}