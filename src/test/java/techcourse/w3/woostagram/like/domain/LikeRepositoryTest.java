package techcourse.w3.woostagram.like.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LikeRepositoryTest {
    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User persistUser1;
    private Article persistArticle;
    private Likes likes1;

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

        persistUser1 = testEntityManager.persist(user1);

        User persistUser2 = testEntityManager.persist(user2);

        Article article1 = Article.builder()
                .contents("hihi")
                .imageUrl("moomin")
                .user(persistUser1)
                .build();

        persistArticle = testEntityManager.persist(article1);

        testEntityManager.persist(article1);

        likes1 = Likes.builder()
                .article(persistArticle)
                .user(persistUser1)
                .build();

        Likes likes2 = Likes.builder()
                .article(persistArticle)
                .user(persistUser2)
                .build();

        testEntityManager.persist(likes1);
        testEntityManager.persist(likes2);
    }

    @Test
    void findAllByArticle_correct_ok() {
        List<Likes> result = likesRepository.findAllByArticle(persistArticle);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findByArticleAndUser_Id_correct_ok() {
        Likes result = likesRepository.findByArticleAndUser_Id(persistArticle, persistUser1.getId());
        assertThat(result).isEqualTo(likes1);
    }
}
