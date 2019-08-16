package com.woowacourse.dsgram.service.assembler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.dto.user.LoginUserDto;
import com.woowacourse.dsgram.service.dto.user.SignUpUserDto;
import com.woowacourse.dsgram.service.dto.user.UserDto;

public class UserAssembler {
    public static User toEntity(SignUpUserDto signUpUserDto) {
        return User.builder()
                .email(signUpUserDto.getEmail())
                .nickName(signUpUserDto.getNickName())
                .password(signUpUserDto.getPassword())
                .userName(signUpUserDto.getUserName())
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

    public static User toEntity(String email, JsonObject userInfo) {
        return User.builder()
                .email(email)
                .nickName(userInfo.get("login").getAsString())
                // TODO: 2019-08-16 ID, Password 는 오또카지...?
                .password("일단아무거나")
                .webSite(userInfo.get("html_url").getAsString())
                .userName(ifBlankName(userInfo))
                .build();
    }

    private static String ifBlankName(JsonObject userInfo) {
        JsonElement name = userInfo.get("name");
        return name.isJsonNull() ? userInfo.get("id").getAsString() : name.getAsString();
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

    public static LoginUserDto toAuthUserDto(User user) {
        return LoginUserDto.builder()
                .nickName(user.getNickName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();
    }
}
