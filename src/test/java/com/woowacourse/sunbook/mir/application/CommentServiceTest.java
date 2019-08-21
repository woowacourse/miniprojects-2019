package com.woowacourse.sunbook.mir.application;

import com.woowacourse.sunbook.application.article.ArticleService;
import com.woowacourse.sunbook.application.user.LoginService;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.mir.application.dto.CommentResponseDto;
import com.woowacourse.sunbook.mir.domain.Comment;
import com.woowacourse.sunbook.mir.domain.CommentFeature;
import com.woowacourse.sunbook.mir.domain.CommentRepository;
import com.woowacourse.sunbook.mir.domain.exception.MismatchAuthException;
import com.woowacourse.sunbook.presentation.excpetion.NotFoundArticleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CommentServiceTest {
    private static final Long ID = 1L;

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private LoginService loginService;

    @Mock
    private ArticleService articleService;

    @Mock
    private Comment comment;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void 댓글_작성() {
        given(loginService.findById(ID)).willReturn(mock(User.class));
        given(articleService.findById(ID)).willReturn(mock(Article.class));
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        given(modelMapper.map(mock(Comment.class), CommentResponseDto.class)).willReturn(mock(CommentResponseDto.class));

        commentService.save(mock(CommentFeature.class), ID, ID);

        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void 댓글_전체_조회() {
        commentService.findAll();

        verify(commentRepository).findAll();
    }

    @Test
    void 없는_게시글_댓글_수정() {
        given(loginService.findById(ID)).willReturn(mock(User.class));
        given(articleService.findById(ID)).willThrow(NotFoundArticleException.class);

        assertThrows(NotFoundArticleException.class, () -> {
            commentService.modify(ID, mock(CommentFeature.class), ID, ID);
        });
    }

    @Test
    void 게시글_댓글_수정_성공() {
        given(loginService.findById(ID)).willReturn(mock(User.class));
        given(articleService.findById(ID)).willReturn(mock(Article.class));
        given(commentRepository.findById(ID)).willReturn(Optional.of(comment));
        doNothing().when(mock(Comment.class)).validateAuth(mock(User.class), mock(Article.class));
        given(modelMapper.map(mock(Comment.class), CommentResponseDto.class)).willReturn(mock(CommentResponseDto.class));

        commentService.modify(ID, mock(CommentFeature.class), ID, ID);

        verify(comment, times(1)).modify(any(CommentFeature.class), any(User.class), any(Article.class));
    }

    @Test
    void 없는_게시글_댓글_삭제() {
        given(loginService.findById(ID)).willReturn(mock(User.class));
        given(articleService.findById(ID)).willThrow(NotFoundArticleException.class);

        assertThrows(NotFoundArticleException.class, () -> {
            commentService.remove(ID, ID, ID);
        });
    }

    @Test
    void 게시글_댓글_삭제_성공() {
        given(loginService.findById(ID)).willReturn(mock(User.class));
        given(articleService.findById(ID)).willReturn(mock(Article.class));
        given(commentRepository.findById(ID)).willReturn(Optional.of(mock(Comment.class)));
        doNothing().when(mock(Comment.class)).validateAuth(mock(User.class), mock(Article.class));
        doNothing().when(commentRepository).delete(mock(Comment.class));
        given(modelMapper.map(mock(Comment.class), CommentResponseDto.class)).willReturn(mock(CommentResponseDto.class));

        commentService.remove(ID, ID, ID);

        verify(commentRepository, times(1)).delete(any(Comment.class));
    }
}