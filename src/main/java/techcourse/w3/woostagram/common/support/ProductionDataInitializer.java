package techcourse.w3.woostagram.common.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import techcourse.w3.woostagram.article.domain.ArticleRepository;
import techcourse.w3.woostagram.comment.domain.CommentRepository;
import techcourse.w3.woostagram.follow.domain.FollowRepository;
import techcourse.w3.woostagram.like.domain.LikesRepository;
import techcourse.w3.woostagram.user.domain.UserRepository;

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

    }
}
