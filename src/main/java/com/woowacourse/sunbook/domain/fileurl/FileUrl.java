package com.woowacourse.sunbook.domain.fileurl;

import com.woowacourse.sunbook.domain.fileurl.exception.InvalidUrlException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@Embeddable
public class FileUrl {
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
    private static final String EMPTY = "";

    @Lob
    @Column(nullable = false)
    private String fileUrl;

    public FileUrl(final String fileUrl) {
        validateUrl(fileUrl);
        this.fileUrl = fileUrl;
    }

    private void validateUrl(final String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (matcher.find() || EMPTY.equals(url)) {
            return;
        }

        throw new InvalidUrlException();
    }
}
