package techcourse.w3.woostagram.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.comment.service.CommentService;
import techcourse.w3.woostagram.like.service.LikesService;
import techcourse.w3.woostagram.tag.domain.HashTag;
import techcourse.w3.woostagram.tag.dto.HashTagArticleDto;
import techcourse.w3.woostagram.tag.dto.TagDto;
import techcourse.w3.woostagram.tag.service.HashTagService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashTagSearchService implements SearchService {
    private static final String HASH_TAG = "#";
    private final HashTagService hashTagService;
    private final LikesService likesService;
    private final CommentService commentService;
    public HashTagSearchService(final HashTagService hashTagService, LikesService likesService, CommentService commentService) {
        this.hashTagService = hashTagService;
        this.likesService = likesService;
        this.commentService = commentService;
    }

    @Override
    public List<TagDto> search(String query) {
        return hashTagService.findByNameContaining(query).stream()
                .map(HashTag::getTag)
                .distinct()
                .map(TagDto::from)
                .collect(Collectors.toList());
    }

    public Page<HashTagArticleDto> getContainsHashTagArticles(String hashTagName, Pageable pageable) {
        return hashTagService.findByName(HASH_TAG + hashTagName,pageable).map((x) -> x.getArticle()).map((article) -> HashTagArticleDto.from(article,
                (long) likesService.findLikedUserByArticleId(article.getId()).size(),
                (long) commentService.countByArticleId(article.getId())
        ));
    }
}
