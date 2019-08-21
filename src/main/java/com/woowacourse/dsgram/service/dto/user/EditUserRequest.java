package com.woowacourse.dsgram.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EditUserRequest {
    @NotNull
    private long id;

    @Size(min = 2, max = 10, message = "닉네임은 2~10자")
    private String nickName;

    @Size(min = 2, max = 10, message = "이름은 2~10자")
    private String userName;

    @Size(min = 4, max = 16, message = "비밀번호는 4~16자")
    private String password;

    private String webSite;
    private String intro;
    private Optional<MultipartFile> file;

    public EditUserRequest(String nickName, String userName, String webSite, String intro, Optional<MultipartFile> file) {
        this.nickName = nickName;
        this.userName = userName;
        this.webSite = webSite;
        this.intro = intro;
        this.file = file;
    }

    public UserDto toUserDto() {
        return new UserDto(id, userName, nickName, password, webSite, intro);
    }
}
