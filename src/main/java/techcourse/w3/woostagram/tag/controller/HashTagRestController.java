package techcourse.w3.woostagram.tag.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.search.service.HashTagSearchService;
import techcourse.w3.woostagram.tag.dto.HashTagArticleDto;

@RestController
@RequestMapping("/api/tags/hash/")
public class HashTagRestController {
    private final HashTagSearchService hashTagSearchService;

    public HashTagRestController(HashTagSearchService hashTagSearchService) {
        this.hashTagSearchService = hashTagSearchService;
    }

    @GetMapping("/{hashTagName}")
    public ResponseEntity<Page<HashTagArticleDto>> read(Pageable pageable, @PathVariable String hashTagName){
        return ResponseEntity.ok(hashTagSearchService.getContainsHashTagArticles(hashTagName,pageable));
    }
}
