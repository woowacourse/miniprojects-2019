package techcourse.w3.woostagram.like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public LikesService(final LikesRepository likesRepository, final UserService userService, final ArticleService articleService) {
        this.likesRepository = likesRepository;
        this.userService = userService;
        this.articleService = articleService;
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
}
