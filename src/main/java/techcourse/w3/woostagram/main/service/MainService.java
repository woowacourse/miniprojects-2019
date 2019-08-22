package techcourse.w3.woostagram.main.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.comment.service.CommentService;
import techcourse.w3.woostagram.follow.service.FollowService;
import techcourse.w3.woostagram.like.service.LikesService;
import techcourse.w3.woostagram.main.dto.MainArticleDto;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainService {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final LikesService likesService;
    private final FollowService followService;

    public MainService(UserService userService, ArticleService articleService, CommentService commentService, FollowService followService, LikesService likesService) {
        this.userService = userService;
        this.articleService = articleService;
        this.commentService = commentService;
        this.followService = followService;
        this.likesService = likesService;
    }

    public Page<MainArticleDto> getFollowingArticles(String userEmail, Pageable pageable) {
        User user = userService.findUserByEmail(userEmail);
        return articleService.findPageByUsers(getFollowingUsers(user), pageable).map((article) -> {
            List<UserInfoDto> likes = likesService.findLikedUserByArticleId(article.getId());

            return MainArticleDto.from(article,
                    commentService.findByArticleId(article.getId(), userEmail),
                    article.isAuthor(user.getId()),
                    (long) likes.size(),
                    likes.contains(UserInfoDto.from(user)));
        });
    }

    private List<User> getFollowingUsers(User user) {
        List<User> followingUsers = followService.getFollowing(user.getId()).stream()
                .map(UserInfoDto::toEntity)
                .collect(Collectors.toList());
        followingUsers.add(user);
        return followingUsers;
    }
}