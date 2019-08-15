package com.woowacourse.zzazanstagram.model.member.vo;

import com.woowacourse.zzazanstagram.model.member.exception.MemberException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.net.HttpURLConnection;
import java.net.URL;

@Embeddable
public class ProfileImage {

    @Column(name = "profile_image_url")
    private String url;

    private ProfileImage(final String url) {
        this.url = validateUrl(url);
    }

    private ProfileImage() {
    }

    public static ProfileImage of(final String url) {
        return new ProfileImage(url);
    }

    private String validateUrl(final String url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new MemberException("리소스가 존재하지 않습니다");
            }
        } catch (Exception e) {
            throw new MemberException("리소스 경로가 올바르지 않습니다");
        }
        return url;
    }

    public ProfileImage updateUrl(String url) {
        return new ProfileImage(url);
    }

    public String getUrl() {
        return url;
    }
}
