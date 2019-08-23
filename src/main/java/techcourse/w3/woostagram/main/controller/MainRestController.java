package techcourse.w3.woostagram.main.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.main.dto.MainArticleDto;
import techcourse.w3.woostagram.main.service.MainService;
import techcourse.w3.woostagram.common.support.LoggedInUser;

@RestController
@RequestMapping("/api/main")
public class MainRestController {
    private final MainService mainService;

    public MainRestController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping
    public ResponseEntity<Page<MainArticleDto>> read(Pageable pageable, @LoggedInUser String email) {
        return ResponseEntity.ok(mainService.getFollowingArticles(email, pageable));
    }
}
