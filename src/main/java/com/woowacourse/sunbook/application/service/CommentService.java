package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.comment.CommentResponseDto;
import com.woowacourse.sunbook.application.exception.NotFoundCommentException;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.comment.Comment;
import com.woowacourse.sunbook.domain.Content;
import com.woowacourse.sunbook.domain.comment.CommentRepository;
import com.woowacourse.sunbook.domain.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(final CommentRepository commentRepository,
                          final UserService userService,
                          final ArticleService articleService,
                          final ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public Long countByArticleId(final Long articleId) {
        return commentRepository.countByArticleId(articleId);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByIdAndArticleId(final Long articleId, final Long commentId) {
        Comment parent = findById(commentId);

        return Collections.unmodifiableList(
                commentRepository.findByParentAndArticleId(parent, articleId).stream()
                        .sorted()
                        .map(comment -> modelMapper.map(comment, CommentResponseDto.class))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public CommentResponseDto save(final Content content,
                                   final Long articleId,
                                   final Long userId,
                                   final Long commentId) {
        User user = userService.findById(userId);
        Article article = articleService.findById(articleId);
        Comment parent = findById(commentId);
        Comment comment = commentRepository.save(new Comment(content, user, article, parent));

        return modelMapper.map(comment, CommentResponseDto.class);
    }

    @Transactional
    public CommentResponseDto modify(final Long commentId,
                                     final Content content,
                                     final Long articleId,
                                     final Long userId) {
        User user = userService.findById(userId);
        Article article = articleService.findById(articleId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        comment.modify(content, user, article);

        return modelMapper.map(comment, CommentResponseDto.class);
    }

    @Transactional
    public Boolean remove(final Long commentId,
                          final Long articleId,
                          final Long userId) {
        User user = userService.findById(userId);
        Article article = articleService.findById(articleId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        comment.validateAuth(user, article);
        commentRepository.delete(comment);

        return true;
    }

    @Transactional(readOnly = true)
    protected Comment findById(final Long commentId) {
        if (commentId == null) {
            return null;
        }

        return commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
    }
}
