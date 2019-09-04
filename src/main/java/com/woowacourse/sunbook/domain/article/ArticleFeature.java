package com.woowacourse.sunbook.domain.article;

import com.woowacourse.sunbook.domain.Content;
import com.woowacourse.sunbook.domain.fileurl.FileUrl;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@Embeddable
public class ArticleFeature {
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
    private static final String EMPTY = "";

    private Content contents;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fileUrl", column = @Column(name = "image_url"))
    })
    private FileUrl imageUrl;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fileUrl", column = @Column(name = "video_url"))
    })
    private FileUrl videoUrl;

    public ArticleFeature(final Content contents, final FileUrl imageUrl, final FileUrl videoUrl) {
        checkEmpty(contents.getContents(), imageUrl.getFileUrl(), videoUrl.getFileUrl());
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }

    private void checkEmpty(final String contents, final String imageUrl, final String videoUrl) {
        if (EMPTY.equals(contents) && EMPTY.equals(imageUrl) && EMPTY.equals(videoUrl)) {
            throw new IllegalArgumentException("내용이 없습니다.");
        }
    }
}
