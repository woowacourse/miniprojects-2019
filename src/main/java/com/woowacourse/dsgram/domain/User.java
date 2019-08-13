package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private UserBasicInfo userBasicInfo;

    @Lob
    private String intro;

    private User(UserBasicInfo userBasicInfo) {
        this.userBasicInfo = userBasicInfo;
    }

    public static User of(UserBasicInfo userBasicInfo) {
        return new User(userBasicInfo);
    }

}
