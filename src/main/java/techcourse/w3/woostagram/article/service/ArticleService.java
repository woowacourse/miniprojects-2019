package techcourse.w3.woostagram.article.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.domain.ArticleRepository;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.exception.ArticleNotFoundException;
import techcourse.w3.woostagram.common.exception.UnAuthorizedException;
import techcourse.w3.woostagram.common.service.StorageService;
import techcourse.w3.woostagram.explore.dto.MypageArticleDto;
import techcourse.w3.woostagram.tag.service.HashTagService;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.service.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final StorageService storageService;
    private final UserService userService;
    private final HashTagService hashTagService;

    public ArticleService(final ArticleRepository articleRepository, final UserService userService, final StorageService storageService, final HashTagService hashTagService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.storageService = storageService;
        this.hashTagService = hashTagService;
    }

    @Transactional
    public Long save(ArticleDto articleDto, String email) {
        User user = userService.findUserByEmail(email);
        String fileUrl = storageService.saveMultipartFile(articleDto.getImageFile());
        Article article = articleDto.toEntity(fileUrl, user);
        hashTagService.save(articleRepository.save(article), article.getContents());
        return article.getId();
    }

    public ArticleDto findById(Long articleId) {
        return ArticleDto.from(findArticleById(articleId));
    }

    @Transactional
    public void update(ArticleDto articleDto, String email) {
        Article article = articleRepository.findById(articleDto.getId()).orElseThrow(ArticleNotFoundException::new);
        validateUser(article, email);

        article.updateContents(articleDto.getContents());
    }

    public void deleteById(Long articleId, String email) {
        Article article = articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        validateUser(article, email);

        articleRepository.deleteById(articleId);
    }

    public Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
    }

    public Page<Article> findPageByUsers(List<User> users, Pageable pageable) {
        return articleRepository.findByUserIn(users, pageable);
    }

    private void validateUser(Article article, String email) {
        User user = userService.findUserByEmail(email);

        if (!article.isAuthor(user.getId())) {
            throw new UnAuthorizedException();
        }
    }


    public Page<Article> findRecommendedArticle(List<User> followUser, Pageable pageable) {
        return articleRepository.findByUserNotIn(followUser, pageable);
    }
}
