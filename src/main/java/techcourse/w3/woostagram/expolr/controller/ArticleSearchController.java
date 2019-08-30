package techcourse.w3.woostagram.expolr.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import techcourse.w3.woostagram.expolr.dto.ArticleSearchDto;
import techcourse.w3.woostagram.expolr.dto.MypageArticleDto;
import techcourse.w3.woostagram.expolr.service.ArticleSearchService;

@RestController
@RequestMapping("/api")
public class ArticleSearchController {
    private final ArticleSearchService articleSearchService;

    public ArticleSearchController(ArticleSearchService articleSearchService) {
        this.articleSearchService = articleSearchService;
    }

    @GetMapping("/main")
    public ResponseEntity<Page<ArticleSearchDto>> readIndex(Pageable pageable, @LoggedInUser String email) {
        return ResponseEntity.ok(articleSearchService.findFollowing(email, pageable));
    }

    @GetMapping("/likes")
    public ResponseEntity<Page<ArticleSearchDto>> readLikes(Pageable pageable, @LoggedInUser String email) {
        return ResponseEntity.ok(articleSearchService.findLikes(email, pageable));
    }

    @GetMapping("/tags/hash//{hashTagName}")
    public ResponseEntity<Page<MypageArticleDto>> readTagging(Pageable pageable, @PathVariable String hashTagName) {
        return ResponseEntity.ok(articleSearchService.findByContainsHashTag(hashTagName, pageable));
    }

    @GetMapping("/mypage/users/{userName}")
    public ResponseEntity<Page<MypageArticleDto>> readUser(Pageable pageable, @PathVariable String userName) {
        return ResponseEntity.ok(articleSearchService.findByUser(userName, pageable));
    }
}
