package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.member.vo.Email;
import com.woowacourse.zzazanstagram.model.member.vo.Name;
import com.woowacourse.zzazanstagram.model.member.vo.NickName;
import com.woowacourse.zzazanstagram.model.member.vo.Profile;

public class MemberResponse {
    private NickName nickName;
    private Name name;
    private Email email;
    private Profile profile;

    public MemberResponse(NickName nickName, Name name, Email email, Profile profile) {
        this.nickName = nickName;
        this.name = name;
        this.email = email;
        this.profile = profile;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
