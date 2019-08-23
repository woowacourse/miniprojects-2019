package com.wootecobook.turkey.commons;

import com.wootecobook.turkey.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GoodResponse {

    private int totalGood;
    private boolean isGood;

    public GoodResponse(final List<? extends Good> goods, final User user) {
        this.totalGood = goods.size();
        this.isGood = goods.stream().anyMatch(good -> good.isGood(user));
    }

    public static GoodResponse init() {
        return new GoodResponse();
    }

    public static GoodResponse of(final List<? extends Good> goods, final User user) {
        return new GoodResponse(goods, user);
    }
}
