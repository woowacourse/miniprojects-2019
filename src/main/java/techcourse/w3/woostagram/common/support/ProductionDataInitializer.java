package techcourse.w3.woostagram.common.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.domain.ArticleRepository;
import techcourse.w3.woostagram.comment.domain.Comment;
import techcourse.w3.woostagram.comment.domain.CommentRepository;
import techcourse.w3.woostagram.follow.domain.Follow;
import techcourse.w3.woostagram.follow.domain.FollowRepository;
import techcourse.w3.woostagram.like.domain.Likes;
import techcourse.w3.woostagram.like.domain.LikesRepository;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;
import techcourse.w3.woostagram.user.domain.UserRepository;

@Profile("prod")
@Component
public class ProductionDataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;
    private final LikesRepository likesRepository;

    @Autowired
    public ProductionDataInitializer(UserRepository userRepository, ArticleRepository articleRepository, CommentRepository commentRepository, FollowRepository followRepository, LikesRepository likesRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.followRepository = followRepository;
        this.likesRepository = likesRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = userRepository.save(User.builder()
                .email("a@naver.com")
                .password("Aa1234!!")
                .userContents(
                        UserContents.builder()
                                .userName("user")
                                .build()
                )
                .build());

        User user2 = userRepository.save(User.builder()
                .email("b@naver.com")
                .password("Aa1234!!")
                .userContents(
                        UserContents.builder()
                                .userName("user2")
                                .build()
                )
                .build());

        User user3 = userRepository.save(User.builder()
                .email("c@naver.com")
                .password("Aa1234!!")
                .userContents(
                        UserContents.builder()
                                .userName("user3")
                                .build()
                )
                .build());

        Article article1 = articleRepository.save(Article.builder()
                .user(user1)
                .contents("moomin is moomin1")
                .imageUrl("https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/feee6754-8855-4461-b154-86cbda2b8864.png")
                .build());

        Article article2 = articleRepository.save(Article.builder()
                .user(user1)
                .contents("moomin is moomin2")
                .imageUrl("https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/feee6754-8855-4461-b154-86cbda2b8864.png")
                .build());

        Article article3 = articleRepository.save(Article.builder()
                .user(user1)
                .contents("moomin is moomin3")
                .imageUrl("https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/feee6754-8855-4461-b154-86cbda2b8864.png")
                .build());

        Comment comment1 = commentRepository.save(Comment.builder()
                .article(article1)
                .contents("contents1")
                .user(user1)
                .build());

        Comment comment2 = commentRepository.save(Comment.builder()
                .article(article1)
                .contents("contents2")
                .user(user1)
                .build());

        Follow follow1 = followRepository.save(Follow.builder()
                .from(user1)
                .to(user2)
                .build());

        Follow follow2 = followRepository.save(Follow.builder()
                .from(user1)
                .to(user3)
                .build());

        Likes likes1 = likesRepository.save(Likes.builder()
                .article(article1)
                .likeUser(user1)
                .build());

        Likes likes2 = likesRepository.save(Likes.builder()
                .article(article1)
                .likeUser(user2)
                .build());

        Likes likes3 = likesRepository.save(Likes.builder()
                .article(article1)
                .likeUser(user3)
                .build());
    }
}
