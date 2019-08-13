package techcourse.w3.woostagram.article.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.assembler.ArticleAssembler;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.domain.ArticleRepository;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.exception.FileSaveFailException;

import javax.transaction.Transactional;
import java.io.File;

@Slf4j
@Service
public class ArticleService {
    @Value("${file.upload.directory}")
    private String UPLOAD_PATH;
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    public void save(ArticleDto articleDto) {
        Article article = ArticleAssembler.toArticle(articleDto, UPLOAD_PATH);
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
