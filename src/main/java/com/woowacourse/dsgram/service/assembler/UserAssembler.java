package com.woowacourse.dsgram.service.assembler;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.dto.oauth.OAuthUserInfoResponse;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import com.woowacourse.dsgram.service.dto.user.UserDto;

import java.util.Optional;

public class UserAssembler {
    public static User toEntity(SignUpUserRequest signUpUserRequest) {
        return User.builder()
                .email(signUpUserRequest.getEmail())
                .nickName(signUpUserRequest.getNickName())
                .password(signUpUserRequest.getPassword())
                .userName(signUpUserRequest.getUserName())
                .intro("")
                .webSite("")
                .build();
    }

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .nickName(userDto.getNickName())
                .intro(userDto.getIntro())
                .webSite(userDto.getWebSite())
                .build();
    }

    public static User toEntity(String email, OAuthUserInfoResponse userInfo) {
        return User.builder()
                .email(email)
                .nickName(String.valueOf(email.hashCode()))
                .password(String.valueOf(email.hashCode()))
                .webSite(userInfo.getHtml_url())
                .userName(ifBlankName(userInfo.getName()))
                .isOAuthUser(true)
                .build();
    }

    private static String ifBlankName(String name) {
        return Optional.ofNullable(name).orElse("이름을 바꿔주세요.");
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .intro(user.getIntro())
                .nickName(user.getNickName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .webSite(user.getWebSite())
                .build();
    }

    public static LoginUserRequest toLoginUser(User user) {
        return LoginUserRequest.builder()
                .nickName(user.getNickName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();
    }
}
