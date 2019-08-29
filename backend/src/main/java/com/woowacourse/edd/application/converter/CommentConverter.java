package com.woowacourse.edd.application.converter;

import com.woowacourse.edd.application.dto.CommentRequestDto;
import com.woowacourse.edd.application.response.CommentResponse;
import com.woowacourse.edd.domain.Comment;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.domain.Video;

import java.time.format.DateTimeFormatter;

public class CommentConverter {

    public static Comment toEntity(Video video, User author, CommentRequestDto commentRequestDto) {
        return new Comment(commentRequestDto.getContents(), video, author);
    }

    public static CommentResponse toResponse(Comment comment) {
        String date = comment.getCreateDate().toString();

        return new CommentResponse(comment.getId(), comment.getContents(), comment.getAuthor(), date);
    }
}
