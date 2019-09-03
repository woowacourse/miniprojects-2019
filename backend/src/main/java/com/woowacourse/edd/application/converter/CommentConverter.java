package com.woowacourse.edd.application.converter;

import com.nhncorp.lucy.security.xss.XssPreventer;
import com.woowacourse.edd.application.dto.CommentRequestDto;
import com.woowacourse.edd.application.response.CommentResponse;
import com.woowacourse.edd.domain.Comment;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.domain.Video;

public class CommentConverter {

    public static Comment toEntity(Video video, User author, CommentRequestDto commentRequestDto) {
        return new Comment(XssPreventer.escape(commentRequestDto.getContents()), video, author);
    }

    public static CommentResponse toResponse(Comment comment) {
        String date = comment.getCreateDate().toString();

        return new CommentResponse(comment.getId(), comment.getContents(), comment.getAuthor(), date);
    }

    public static CommentRequestDto escapeUpdateRequestDto(CommentRequestDto updateRequestDto) {
        return new CommentRequestDto(XssPreventer.escape(updateRequestDto.getContents()));
    }
}
