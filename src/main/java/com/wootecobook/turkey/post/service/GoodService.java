package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.commons.Good;
import com.wootecobook.turkey.user.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GoodService<T extends Good, D> {

    List<T> toggleGood(D d, User user);

    @Transactional(readOnly = true)
    List<T> findBy(D d);

}
