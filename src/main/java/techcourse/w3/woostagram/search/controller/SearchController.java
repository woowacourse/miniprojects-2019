package techcourse.w3.woostagram.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.search.service.SearchService;
import techcourse.w3.woostagram.search.service.SearchServiceFactory;

@RestController
@RequestMapping("/api/search/{searchQuery}")
public class SearchController {
    private final SearchServiceFactory searchTypeService;
    public SearchController(SearchServiceFactory searchTypeService) {
        this.searchTypeService = searchTypeService;
    }

    @GetMapping
    public ResponseEntity read(@PathVariable String searchQuery) {
         SearchService searchService = searchTypeService.getSearchService(searchQuery);
         return ResponseEntity.ok(searchService.search(searchQuery));
    }
}
