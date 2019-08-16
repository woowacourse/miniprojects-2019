package techcourse.w3.woostagram.article.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.domain.ArticleRepository;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.exception.ArticleNotFoundException;
import techcourse.w3.woostagram.common.service.FileService;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.service.UserService;

import javax.transaction.Transactional;

@Slf4j
@Service
public class ArticleService {
    private static final String PROJECT_NAME = "miniprojects-2019";
    private static final int IMAGE_URL_INDEX = 1;

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final FileService fileService;

    public ArticleService(final ArticleRepository articleRepository, final UserService userService, final FileService fileService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.fileService = fileService;
    }

    @Transactional
    public Long save(ArticleDto articleDto, String email) {
        User user = userService.findUserByEmail(email);
        String filePath = fileService.saveMultipartFile(articleDto.getImageFile());
        Article article = articleDto.toEntity(filePath.split(PROJECT_NAME)[IMAGE_URL_INDEX], user);
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
