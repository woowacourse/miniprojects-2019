package techcourse.w3.woostagram.main.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.comment.service.CommentService;
import techcourse.w3.woostagram.follow.service.FollowService;
import techcourse.w3.woostagram.main.dto.MainArticleDto;
import techcourse.w3.woostagram.main.dto.MainPageDto;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainService {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final FollowService followService;
    private final UserService userService;

    public MainService(UserService userService, ArticleService articleService, CommentService commentService, FollowService followService) {
        this.userService = userService;
        this.articleService = articleService;
        this.commentService = commentService;
        this.followService = followService;
    }

    public Page<MainArticleDto> getFollowingArticles(String userEmail, Pageable pageable) {
        User user = userService.findUserByEmail(userEmail);
        List<User> followingUsers = followService.getFollowing(user.getId()).stream()
                .map(UserInfoDto::toEntity)
                .collect(Collectors.toList());
        followingUsers.add(user);
        return  articleService.findPageByUsers(followingUsers, pageable).map((article) ->
            MainArticleDto.from(article,
                    commentService.findByArticleId(article.getId()),
                    article.isAuthor(user.getId()))
        );
    }
}