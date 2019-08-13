package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.member.vo.*;

public class Member {
    private NickName nickName;
    private Name name;
    private Email email;
    private Password password;
    private Profile profile;

    public static final class MemberBuilder {
        private NickName nickName;
        private Name name;
        private Email email;
        private Password password;
        private Profile profile;

        private MemberBuilder() {
        }

        public static MemberBuilder aMember() {
            return new MemberBuilder();
        }

        public MemberBuilder nickName(String nickName) {
            this.nickName = NickName.of(nickName);
            return this;
        }

        public MemberBuilder name(String name) {
            this.name = Name.of(name);
            return this;
        }

        public MemberBuilder email(String email) {
            this.email = Email.of(email);
            return this;
        }

        public MemberBuilder password(String password) {
            this.password = Password.of(password);
            return this;
        }

        public MemberBuilder profile(String profile) {
            this.profile = Profile.of(profile);
            return this;
        }

        public Member build() {
            Member member = new Member();
            member.name = this.name;
            member.nickName = this.nickName;
            member.email = this.email;
            member.password = this.password;
            member.profile = this.profile;
            return member;
        }
    }
}
