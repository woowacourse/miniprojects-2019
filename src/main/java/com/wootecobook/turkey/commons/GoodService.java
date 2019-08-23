package com.wootecobook.turkey.commons;

import com.wootecobook.turkey.user.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GoodService<T extends Good, D> {

    List<T> toggleGood(D d, User user);

    @Transactional(readOnly = true)
    List<T> findBy(D d);

}
