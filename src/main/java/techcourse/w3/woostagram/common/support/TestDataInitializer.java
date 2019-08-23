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

@Profile("test")
@Component
public class TestDataInitializer implements ApplicationRunner {
    public static User authorUser;
    public static User updateUser;
    public static User deleteUser;
    public static User unAuthorUser;

    public static Article basicArticle;
    public static Article updateArticle;
    public static Article deleteArticle;

    public static Comment basicComment;
    public static Comment updateComment;
    public static Comment deleteComment;

    public static Follow basicFollow;
    public static Follow deleteFollow;

    public static Likes basicLikes;
    public static Likes deleteLikes;

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;
    private final LikesRepository likesRepository;

    @Autowired
    public TestDataInitializer(UserRepository userRepository, ArticleRepository articleRepository, CommentRepository commentRepository, FollowRepository followRepository, LikesRepository likesRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.followRepository = followRepository;
        this.likesRepository = likesRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        authorUser = saveUser("a@naver.com", "Aa1234!!", "user1");
        updateUser = saveUser("b@naver.com", "Aa1234!!", "user2");
        deleteUser = saveUser("c@naver.com", "Aa1234!!", "user3");
        unAuthorUser = saveUser("d@naver.com", "Aa1234!!", "user4");

        basicArticle = saveArticle(authorUser, "moomin is moomin1", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/feee6754-8855-4461-b154-86cbda2b8864.png");
        updateArticle = saveArticle(authorUser, "moomin is moomin2", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/feee6754-8855-4461-b154-86cbda2b8864.png");
        deleteArticle = saveArticle(authorUser, "moomin is moomin3", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/feee6754-8855-4461-b154-86cbda2b8864.png");

        basicComment = saveComment(basicArticle, "contents1", authorUser);
        updateComment = saveComment(basicArticle, "contents2", authorUser);
        deleteComment = saveComment(basicArticle, "contents3", authorUser);

        basicFollow = saveFollow(authorUser, unAuthorUser);
        deleteFollow = saveFollow(unAuthorUser, authorUser);

        basicLikes = saveLikes(authorUser, basicArticle);
        deleteLikes = saveLikes(authorUser, updateArticle);
    }

    private Likes saveLikes(User liker, Article article) {
        return likesRepository.save(Likes.builder()
                .article(article)
                .user(liker)
                .build());
    }

    private Follow saveFollow(User from, User to) {
        return followRepository.save(Follow.builder()
                .from(from)
                .to(to)
                .build());
    }

    private Comment saveComment(Article article, String contents, User commenter) {
        return commentRepository.save(Comment.builder()
                .article(article)
                .contents(contents)
                .user(commenter)
                .build());
    }

    private Article saveArticle(User author, String contents, String image) {
        return articleRepository.save(Article.builder()
                .user(author)
                .contents(contents)
                .imageUrl(image)
                .build());
    }

    private User saveUser(String email, String password, String userName) {
        return userRepository.save(User.builder()
                .email(email)
                .password(password)
                .userContents(
                        UserContents.builder()
                                .userName(userName)
                                .build()
                )
                .build());
    }
}
