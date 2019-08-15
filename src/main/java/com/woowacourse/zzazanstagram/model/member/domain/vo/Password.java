package com.woowacourse.zzazanstagram.model.member.domain.vo;

import com.woowacourse.zzazanstagram.model.member.exception.MemberException;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Password {
    private static final String PASSWORD_REGEX = ".*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*";

    @Column(name = "password")
    private String password;

    private Password(final String password) {
        this.password = validatePassword(password);
    }

    private Password() {
    }

    public static Password of(final String password) {
        return new Password(password);
    }

    private String validatePassword(final String password) {
        if (!password.matches(PASSWORD_REGEX)) {
            throw new MemberException("잘못된 형식의 비밀번호입니다.");
        }
        return encrypt(password);
    }

    private String encrypt(String password) {
        return Encrypt.encrypt(password);
    }

    public boolean isMatch(String password) {
        return Encrypt.isMatch(password, this.password);
    }

    public String getPassword() {
        return password;
    }

    private static class Encrypt {
        static String encrypt(String password) {
            return BCrypt.hashpw(password, BCrypt.gensalt());
        }

        static boolean isMatch(String password, String hashed) {
            return BCrypt.checkpw(password, hashed);
        }
    }
}
