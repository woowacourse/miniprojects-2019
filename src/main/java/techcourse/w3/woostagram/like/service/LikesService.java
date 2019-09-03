package techcourse.w3.woostagram.like.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.w3.woostagram.alarm.service.AlarmService;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.like.domain.Likes;
import techcourse.w3.woostagram.like.domain.LikesRepository;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserService userService;
    private final ArticleService articleService;
    private final AlarmService alarmService;

    public LikesService(final LikesRepository likesRepository, final UserService userService,
                        final ArticleService articleService, final AlarmService alarmService) {
        this.likesRepository = likesRepository;
        this.userService = userService;
        this.articleService = articleService;
        this.alarmService = alarmService;
    }

    @Transactional
    public void save(String email, Long articleId) {
        User user = userService.findUserByEmail(email);
        Article article = articleService.findArticleById(articleId);

        Likes likes = Likes.builder()
                .article(article)
                .user(user)
                .build();

        likesRepository.save(likes);
        if (!article.isAuthor(user.getId())) {
            alarmService.pushLikes(user, article);
        }
    }

    public List<UserInfoDto> findLikedUserByArticleId(Long articleId) {
        Article article = articleService.findArticleById(articleId);
        List<Likes> likedUsers = likesRepository.findAllByArticle(article);
        return likedUsers.stream().map(likes -> UserInfoDto.from(likes.getUser())).collect(Collectors.toList());
    }

    public void delete(Long articleId, String email) {
        User user = userService.findUserByEmail(email);
        Article article = articleService.findArticleById(articleId);
        Likes likes = likesRepository.findByArticleAndUser_Id(article, user.getId());
        likesRepository.delete(likes);
    }

    public Page<Likes> findLikesByEmail(String email, Pageable pageable) {
        User user = userService.findUserByEmail(email);
        return likesRepository.findAllByUser(user, pageable);
    }
}
