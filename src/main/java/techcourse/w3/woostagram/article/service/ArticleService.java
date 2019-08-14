package techcourse.w3.woostagram.article.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.assembler.ArticleAssembler;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.domain.ArticleRepository;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.exception.FileSaveFailException;

import javax.transaction.Transactional;
import java.io.File;
import java.util.UUID;

@Slf4j
@Service
public class ArticleService {
//    @Value("${file.upload.directory}")
    private static final String UPLOAD_PATH = "/home/yumin/Codes/WoowaTech/Level2/miniprojects-2019/src/main/resources/static/uploaded/";
    private final ArticleRepository articleRepository;

    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    public void save(ArticleDto articleDto) {
        String fullPath = String.join(".", UPLOAD_PATH + UUID.randomUUID().toString(),
                FilenameUtils.getExtension(articleDto.getImageFile().getOriginalFilename()));
        Article article = ArticleAssembler.toArticle(articleDto, fullPath);
        File file = new File(article.getImageUrl());
        try {
            file.createNewFile();
            articleRepository.save(article);
            articleDto.getImageFile().transferTo(file);
        } catch(Exception e) {
            file.delete();
            throw new FileSaveFailException("서버에 정적 파일을 업로드 하는 데 실패했습니다.");
        }
    }
}
