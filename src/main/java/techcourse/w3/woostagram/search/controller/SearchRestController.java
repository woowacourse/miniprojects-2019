package techcourse.w3.woostagram.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.search.dto.SearchDto;
import techcourse.w3.woostagram.search.service.FullSearchService;

@RestController
@RequestMapping("/api/search/{searchQuery}")
public class SearchRestController {
    private final FullSearchService fullSearchService;

    public SearchRestController(FullSearchService fullSearchService) {
        this.fullSearchService = fullSearchService;
    }

    @GetMapping
    public ResponseEntity<SearchDto> read(@PathVariable String searchQuery) {
        return ResponseEntity.ok(fullSearchService.search(searchQuery));
    }
}
