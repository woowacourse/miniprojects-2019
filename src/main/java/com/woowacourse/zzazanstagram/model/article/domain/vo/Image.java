package com.woowacourse.zzazanstagram.model.article.domain.vo;

import com.woowacourse.zzazanstagram.model.article.exception.ArticleException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.net.HttpURLConnection;
import java.net.URL;

@Embeddable
public class Image {

    @Column(name = "image_url", nullable = false)
    private String url;

    private Image() {
    }

    private Image(final String url) {
        this.url = validateUrl(url);
    }

    private String validateUrl(final String url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new ArticleException("리소스가 존재하지 않습니다");
            }
        } catch (Exception e) {
            throw new ArticleException("리소스 경로가 올바르지 않습니다");
        }
        return url;
    }

    public static Image of(final String imageUrl) {
        return new Image(imageUrl);
    }

    public String getUrl() {
        return url;
    }
}