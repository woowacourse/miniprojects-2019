package techcourse.w3.woostagram.tag.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HashTagRepositoryTest {

    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findTagTop10ByTag_NameContainingIgnoreCaseOrderByTag_Name() {
        User user = User.builder()
                .email("ab@naver.com")
                .password("Aa12345!!")
                .userContents(UserContents.builder()
                        .userName("user1")
                        .build())
                .build();
        User persistUser = testEntityManager.persist(user);

        Article article = Article.builder()
                .contents("hihi")
                .imageUrl("moomin")
                .user(persistUser)
                .build();

        Article persistArticle = testEntityManager.persist(article);

        Tag tag = Tag.builder()
                .name("hippo")
                .build();
        Tag persistTag = testEntityManager.persist(tag);

        HashTag hashTag = HashTag.builder()
                .tag(persistTag)
                .article(persistArticle)
                .build();

        HashTag persistHashTag = testEntityManager.persist(hashTag);

        assertThat(hashTagRepository.findTop10ByTag_NameContainingIgnoreCaseOrderByTag_Name("hi")).contains(persistHashTag);

    }
}