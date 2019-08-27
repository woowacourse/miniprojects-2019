package techcourse.w3.woostagram.tag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.tag.domain.HashTag;
import techcourse.w3.woostagram.tag.domain.HashTagRepository;
import techcourse.w3.woostagram.tag.domain.Tag;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class HashTagService {
    private static final Pattern HASH_PATTERN = Pattern.compile("#[A-Za-zㄱ-힣0-9_]+");

    private final TagService tagService;
    private final HashTagRepository hashTagRepository;

    public HashTagService(final TagService tagService, final HashTagRepository hashTagRepository) {
        this.tagService = tagService;
        this.hashTagRepository = hashTagRepository;
    }

    @Transactional
    public void save(Article article, String contents) {
        List<Tag> tags = tagService.parse(contents, HASH_PATTERN);
        tags.forEach(tag ->
                hashTagRepository.save(HashTag.builder()
                        .article(article)
                        .tag(tag)
                        .build())
        );
    }

    public List<HashTag> findByNameContaining(String query) {
        return hashTagRepository.findTop10ByTag_NameContainingIgnoreCaseOrderByTag_Name(query);
    }
}