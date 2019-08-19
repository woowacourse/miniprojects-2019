package techcourse.w3.woostagram.article.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.domain.ArticleRepository;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.exception.ArticleNotFoundException;
import techcourse.w3.woostagram.common.service.StorageService;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.service.UserService;

import javax.transaction.Transactional;

@Slf4j
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final StorageService storageService;

    public ArticleService(final ArticleRepository articleRepository, final UserService userService, final StorageService storageService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.storageService = storageService;
    }

    @Transactional
    public Long save(ArticleDto articleDto, String email) {
        User user = userService.findUserByEmail(email);
        String fileUrl = storageService.saveMultipartFile(articleDto.getImageFile());
        Article article = articleDto.toEntity(fileUrl, user);
        articleRepository.save(article);
        return article.getId();
    }

    public ArticleDto findById(Long articleId) {
        return ArticleDto.from(articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new));
    }

    @Transactional
    public void update(ArticleDto articleDto) {
        Article article = articleRepository.findById(articleDto.getId()).orElseThrow(ArticleNotFoundException::new);
        article.updateContents(articleDto.getContents());
    }

    public void deleteById(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
