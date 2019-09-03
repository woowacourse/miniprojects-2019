package techcourse.w3.woostagram.explore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import techcourse.w3.woostagram.explore.dto.ArticleSearchDto;
import techcourse.w3.woostagram.explore.dto.MypageArticleDto;
import techcourse.w3.woostagram.explore.service.ExploreService;

@RestController
@RequestMapping("/api")
public class ExploreRestController {
    private final ExploreService articleSearchService;

    public ExploreRestController(final ExploreService articleSearchService) {
        this.articleSearchService = articleSearchService;
    }

    @GetMapping("/main")
    public ResponseEntity<Page<ArticleSearchDto>> listIndex(Pageable pageable, @LoggedInUser String email) {
        return ResponseEntity.ok(articleSearchService.findFollowing(email, pageable));
    }

    @GetMapping("/likes")
    public ResponseEntity<Page<ArticleSearchDto>> listLikes(Pageable pageable, @LoggedInUser String email) {
        return ResponseEntity.ok(articleSearchService.findLikes(email, pageable));
    }

    @GetMapping("/tags/hash//{hashTagName}")
    public ResponseEntity<Page<MypageArticleDto>> listTagging(Pageable pageable, @PathVariable String hashTagName) {
        return ResponseEntity.ok(articleSearchService.findByContainsHashTag(hashTagName, pageable));
    }

    @GetMapping("/mypage/users/{userName}")
    public ResponseEntity<Page<MypageArticleDto>> listUser(Pageable pageable, @PathVariable String userName) {
        return ResponseEntity.ok(articleSearchService.findByUser(userName, pageable));
    }

    @GetMapping("/recommended")
    public ResponseEntity<Page<ArticleSearchDto>> readRecommended(Pageable pageable, @LoggedInUser String email) {
        return ResponseEntity.ok(articleSearchService.findRecommendedArticle(email, pageable));
    }
}
