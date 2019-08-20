package techcourse.w3.woostagram.comment.service;

import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.comment.domain.Comment;
import techcourse.w3.woostagram.comment.domain.CommentRepository;
import techcourse.w3.woostagram.comment.dto.CommentDto;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.service.UserService;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;

    public CommentService(CommentRepository commentRepository, UserService userService, ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.articleService = articleService;
    }

    public CommentDto save(CommentDto commentDto, String email) {
        User user = userService.findUserByEmail(email);
        Article article = articleService.findArticleById(commentDto.getArticleId());
        Comment comment = commentRepository.save(commentDto.toEntity(user, article));
        return CommentDto.from(comment);
    }
}
