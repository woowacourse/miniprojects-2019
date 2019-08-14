package techcourse.w3.woostagram.article.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.assembler.ArticleAssembler;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.domain.ArticleRepository;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.exception.ArticleNotFoundException;
import techcourse.w3.woostagram.article.exception.FileSaveFailException;
import techcourse.w3.woostagram.article.exception.InvalidExtensionException;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.service.UserService;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ArticleService {
//    @Value("${file.upload.directory}")
    private static final String UPLOAD_PATH = "/home/yumin/Codes/WoowaTech/Level2/miniprojects-2019/uploads/";
    private final ArticleRepository articleRepository;
    private final UserService userService;
    private List<String> validExtensions = Arrays.asList("jpg", "jpeg", "png");

    public ArticleService(final ArticleRepository articleRepository, final UserService userService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    @Transactional
    public Long save(ArticleDto articleDto, String email) {
        String fileExtension = FilenameUtils.getExtension(articleDto.getImageFile().getOriginalFilename());
        if (fileExtension == null || !validExtensions.contains(fileExtension.toLowerCase())) {
            throw new InvalidExtensionException();
        }

        User user = userService.findEntityByEmail(email);
        String fullPath = String.join(".", UPLOAD_PATH + UUID.randomUUID().toString(), fileExtension);
        Article article = ArticleAssembler.toArticle(articleDto, fullPath.split("miniprojects-2019")[1], user);
        File file = new File(fullPath);
        try {
            file.createNewFile();
            articleRepository.save(article);
            articleDto.getImageFile().transferTo(file);
            return article.getId();
        } catch(Exception e) {
            file.delete();
            throw new FileSaveFailException();
        }
    }

    public ArticleDto get(Long articleId) {
        return ArticleAssembler.toArticleDto(articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new));
    }

    @Transactional
    public void update(ArticleDto articleDto) {
        Article article = articleRepository.findById(articleDto.getId()).orElseThrow(ArticleNotFoundException::new);
        article.update(articleDto.getContents());
    }

    public void remove(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
