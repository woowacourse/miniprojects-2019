package techcourse.w3.woostagram.explore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.comment.service.CommentService;
import techcourse.w3.woostagram.explore.dto.ArticleSearchDto;
import techcourse.w3.woostagram.explore.dto.MypageArticleDto;
import techcourse.w3.woostagram.follow.service.FollowService;
import techcourse.w3.woostagram.like.service.LikesService;
import techcourse.w3.woostagram.tag.domain.HashTag;
import techcourse.w3.woostagram.tag.service.HashTagService;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExploreService {
    private static final String HASH_TAG = "#";

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final LikesService likesService;
    private final FollowService followService;
    private final HashTagService hashTagService;

    public ExploreService(ArticleService articleService, CommentService commentService, UserService userService, LikesService likesService, FollowService followService, HashTagService hashTagService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
        this.likesService = likesService;
        this.followService = followService;
        this.hashTagService = hashTagService;
    }

    public Page<ArticleSearchDto> findFollowing(String userEmail, Pageable pageable) {
        User user = userService.findUserByEmail(userEmail);
        return articleService.findPageByUsers(findFollowingUsers(user), pageable).map(getArticleSearchDtoFunction(userEmail, user));
    }

    private Function<Article, ArticleSearchDto> getArticleSearchDtoFunction(String userEmail, User user) {
        return (article) -> {
            List<UserInfoDto> likes = likesService.findLikedUserByArticleId(article.getId());

            return ArticleSearchDto.from(article,
                    commentService.findByArticleId(article.getId(), userEmail),
                    article.isAuthor(user.getId()),
                    (long) likes.size(),
                    likes.contains(UserInfoDto.from(user)));
        };
    }

    private List<User> findFollowingUsers(User user) {
        List<User> followingUsers = followService.findAllByFrom(user.getId()).stream()
                .map(UserInfoDto::toEntity)
                .collect(Collectors.toList());
        followingUsers.add(user);
        return followingUsers;
    }

    public Page<ArticleSearchDto> findLikes(String email, Pageable pageable) {
        User user = userService.findUserByEmail(email);
        return likesService.findLikesByEmail(email, pageable).map(likes ->
                ArticleSearchDto.from(likes.getArticle(),
                        commentService.findByArticleId(likes.getArticle().getId(), email),
                        likes.getArticle().isAuthor(user.getId()),
                        (long) likesService.findLikedUserByArticleId(likes.getArticle().getId()).size(),
                        true)
        );
    }

    public Page<MypageArticleDto> findByContainsHashTag(String hashTagName, Pageable pageable) {
        return hashTagService.findByName(HASH_TAG + hashTagName, pageable).map(HashTag::getArticle).map((article) -> MypageArticleDto.from(article,
                (long) likesService.findLikedUserByArticleId(article.getId()).size(),
                (long) commentService.countByArticleId(article.getId())
        ));
    }

    public Page<MypageArticleDto> findByUser(String userName, Pageable pageable) {
        User user = userService.findUserByUserName(userName);
        return articleService.findPageByUsers(Collections.singletonList(user), pageable).map((article) -> MypageArticleDto.from(article,
                (long) likesService.findLikedUserByArticleId(article.getId()).size(),
                (long) commentService.countByArticleId(article.getId())
        ));
    }

    public Page<ArticleSearchDto> findRecommendedArticle(String email, Pageable pageable) {
        User user = userService.findUserByEmail(email);
        return articleService.findRecommendedArticle(findFollowingUsers(user), pageable)
                .map(getArticleSearchDtoFunction(email, user));
    }
}
