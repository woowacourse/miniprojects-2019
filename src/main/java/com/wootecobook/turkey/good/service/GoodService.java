package com.wootecobook.turkey.good.service;

import com.wootecobook.turkey.user.domain.User;
import org.springframework.transaction.annotation.Transactional;

public interface GoodService<D> {

    int toggleGood(D d, User user);

    @Transactional(readOnly = true)
    int countBy(D d);

    @Transactional(readOnly = true)
    boolean existsByPostAndUser(D d, User user);
}
