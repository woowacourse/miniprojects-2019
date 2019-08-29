package com.woowacourse.edd.repository;

import com.woowacourse.edd.domain.Subscription;
import com.woowacourse.edd.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllBySubscribed(User subscribed);

    List<Subscription> findAllBySubscriber(User subscriber);

    void deleteBySubscribedAndSubscriber(User subscribed, User subscriber);

    boolean existsBySubscribedAndSubscriber(User subscribed, User subscriber);
}
