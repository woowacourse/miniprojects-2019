package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.member.vo.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberTest {
    @ParameterizedTest
    @ValueSource(strings = {"1", "11111111111"})
    @DisplayName("닉네임 비정상 체크")
    void nick_name_error_check(String nickName) {
        assertThrows(IllegalArgumentException.class, () ->
                Member.MemberBuilder.aMember().nickName(nickName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "nick"})
    @DisplayName("닉네임 정상 체크")
    void nick_name_check(String nickName) {
        assertThatCode(() ->
                Member.MemberBuilder.aMember().nickName(nickName))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "11111111111"})
    @DisplayName("이름 비정상 체크")
    void name_error_check(String name) {
        assertThrows(IllegalArgumentException.class, () ->
                Member.MemberBuilder.aMember().name(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "nicky"})
    @DisplayName("이름 정상 체크")
    void name_check(String name) {
        assertThatCode(() ->
                Member.MemberBuilder.aMember().name(name))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"dfsdfsdf", "email@"})
    @DisplayName("이메일 비정상 체크")
    void email_error_check(String email) {
        assertThrows(IllegalArgumentException.class, () ->
                Member.MemberBuilder.aMember().email(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@gmail.com", "dfsdfsdf@naver.com"})
    @DisplayName("이메일 정상 체크")
    void email_check(String email) {
        assertThatCode(() ->
                Member.MemberBuilder.aMember().email(email))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"dfdf", "dfsf/!", "DDff"})
    @DisplayName("비밀번호 비정상 체크")
    void password_error_check(String password) {
        assertThrows(IllegalArgumentException.class, () ->
                Member.MemberBuilder.aMember().password(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password!1", "dffDzzzF2!"})
    @DisplayName("비밀번호 정상 체크")
    void password_check(String password) {
        assertThatCode(() ->
                Member.MemberBuilder.aMember().password(password))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("원문과 암호화된 비밀번호 비교")
    void password_matching() {
        String input = "Password!1";
        Password password = Password.of(input);
        assertThat(password.isMatch(input)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"sdfsdfsdf", "notUrl"})
    @DisplayName("프로필 url 비정상 체크")
    void profile_url_error_check(String profile) {
        assertThrows(IllegalArgumentException.class, () ->
                Member.MemberBuilder.aMember().profile(profile));
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://image.shutterstock.com/image-photo/white-transparent-leaf-on-mirror-600w-1029171697.jpg", "https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-600w-1048185397.jpg"})
    @DisplayName("프로필 url 정상 체크")
    void profile_url_check(String profile) {
        assertThatCode(() ->
                Member.MemberBuilder.aMember().profile(profile))
                .doesNotThrowAnyException();
    }
}