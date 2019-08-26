package techcourse.w3.woostagram.mypage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.comment.service.CommentService;
import techcourse.w3.woostagram.like.service.LikesService;
import techcourse.w3.woostagram.mypage.dto.MypageArticleDto;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.Arrays;

@Service
public class MypageService {
    private final ArticleService articleService;
    private final UserService userService;
    private final CommentService commentService;
    private final LikesService likesService;

    public MypageService(ArticleService articleService, UserService userService, CommentService commentService, LikesService likesService) {
        this.articleService = articleService;
        this.userService = userService;
        this.commentService = commentService;
        this.likesService = likesService;
    }

    public Page<MypageArticleDto> getMypageArticles(String userEmail, Pageable pageable){
        User user = userService.findUserByEmail(userEmail);
        return articleService.findPageByUsers(Arrays.asList(user),pageable).map((article)-> MypageArticleDto.from(article,
                (long)likesService.findLikedUserByArticleId(article.getId()).size(),
                (long)commentService.findByArticleId(article.getId(),userEmail).size()
        ));
    }
}
