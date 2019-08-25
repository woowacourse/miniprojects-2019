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
        User gd = saveUser("gyudong@woowahan.com", "Aa1234!!", "gyudong");
        User mm = saveUser("moomin@woowahan.com", "Aa1234!!", "moomin");
        User iv = saveUser("iva@woowahan.com", "Aa1234!!", "iva", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/default_profile_image.jpg");
        User hr = saveUser("harry-potter@woowahan.com", "Aa1234!!", "harry");
        User uni = saveUser("uni@woowahan.com", "Aa1234!!", "uni");

        Article gdArticle1 = saveArticle(gd, "gyudong's awesome design artwork", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/gd.png");
        Article mmArticle1 = saveArticle(mm, "moomin is moomin", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/mm.png");
        Article ivArticle1 = saveArticle(iv, "hello world", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/harry_moomin_gd.png");
        Article hrArticle1 = saveArticle(hr, "", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/harry.jpeg");
        Article uniArticle1 = saveArticle(uni, "", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/uni.png");

        Comment mmComment = saveComment(gdArticle1, "대박대박", mm);
        Comment ivComment = saveComment(gdArticle1, "역규(역시 규동이라는 뜻)", iv);
        Comment ivComment2 = saveComment(hrArticle1, "역해(역시 해리라는 뜻)", iv);

        Follow gdToMm = saveFollow(gd, mm);
        Follow gdToIv = saveFollow(gd, iv);
        Follow gdToHr = saveFollow(gd, hr);
        Follow gdToUni = saveFollow(gd, uni);
        Follow mmToGd = saveFollow(mm, gd);
        Follow ivToHr = saveFollow(iv, hr);
        Follow hrToUni = saveFollow(hr, uni);
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

    private User saveUser(String email, String password, String userName, String profile) {
        return userRepository.save(User.builder()
                .email(email)
                .password(password)
                .profile(profile)
                .userContents(
                        UserContents.builder()
                                .userName(userName)
                                .build()
                )
                .build());
    }
}
