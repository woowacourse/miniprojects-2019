package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.member.vo.Email;
import com.woowacourse.zzazanstagram.model.member.vo.Name;
import com.woowacourse.zzazanstagram.model.member.vo.NickName;
import com.woowacourse.zzazanstagram.model.member.vo.ProfileImage;

public class MemberResponse {
    private NickName nickName;
    private Name name;
    private Email email;
    private ProfileImage profileImage;

    public MemberResponse(NickName nickName, Name name, Email email, ProfileImage profileImage) {
        this.nickName = nickName;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }

    public NickName getNickName() {
        return nickName;
    }

    public void setNickName(NickName nickName) {
        this.nickName = nickName;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }
}
