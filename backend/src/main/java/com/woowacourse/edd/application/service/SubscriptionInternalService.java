package com.woowacourse.edd.application.service;

import com.woowacourse.edd.domain.Subscription;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.exceptions.DuplicateSubscriptionException;
import com.woowacourse.edd.exceptions.SelfSubscriptionException;
import com.woowacourse.edd.exceptions.UserNotFoundException;
import com.woowacourse.edd.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SubscriptionInternalService {

    private final UserInternalService userInternalService;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionInternalService(UserInternalService userInternalService, SubscriptionRepository subscriptionRepository) {
        this.userInternalService = userInternalService;
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription save(Long subscribedId, Long subscriberId) {
        checkSelfSubscription(subscribedId, subscriberId);
        List<Long> userIds = Arrays.asList(subscribedId, subscriberId);
        List<User> users = userInternalService.findByIds(userIds);

        User subscribed = findById(users, subscribedId);
        User subscriber = findById(users, subscriberId);

        checkDuplicateSubscription(subscribed, subscriber);

        Subscription subscription = new Subscription(subscriber, subscribed);
        return subscriptionRepository.save(subscription);
    }

    private User findById(List<User> users, Long id) {
        return users.stream()
            .filter(user -> user.getId() == id)
            .findAny()
            .orElseThrow(UserNotFoundException::new);
    }

    private void checkDuplicateSubscription(User subscribed, User subscriber) {
        if (subscriptionRepository.existsBySubscribedAndSubscriber(subscribed, subscriber)) {
            throw new DuplicateSubscriptionException();
        }
    }

    private void checkSelfSubscription(Long subscribedId, Long userId) {
        if (subscribedId == userId) {
            throw new SelfSubscriptionException();
        }
    }

    public int countSubscribers(Long subscribedId) {
        User subscribed = userInternalService.findById(subscribedId);
        return subscriptionRepository.findAllBySubscribed(subscribed).size();
    }

    public List<Subscription> findSubscriptions(Long userId) {
        User user = userInternalService.findById(userId);
        return subscriptionRepository.findAllBySubscriber(user);
    }

    public void cancelSubscription(Long subscribedId, Long userId) {
        checkSelfSubscription(userId, subscribedId);

        User user = userInternalService.findById(userId);
        User subscribed = userInternalService.findById(subscribedId);

        subscriptionRepository.deleteBySubscribedAndSubscriber(subscribed, user);
    }
}
