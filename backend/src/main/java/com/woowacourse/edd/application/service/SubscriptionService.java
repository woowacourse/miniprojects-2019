package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.SubscriptionConverter;
import com.woowacourse.edd.application.response.SubscriptionCountResponse;
import com.woowacourse.edd.application.response.SubscriptionResponse;
import com.woowacourse.edd.domain.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubscriptionService {
    private final SubscriptionInternalService subscriptionInternalService;

    @Autowired
    public SubscriptionService(SubscriptionInternalService subscriptionInternalService) {
        this.subscriptionInternalService = subscriptionInternalService;
    }

    public void subscribe(Long subscribedId, Long id) {
        subscriptionInternalService.save(subscribedId, id);
    }

    public SubscriptionCountResponse countSubscribers(Long subscribedId) {
        int count = subscriptionInternalService.countSubscribers(subscribedId);
        return SubscriptionConverter.toResponse(count);
    }

    public List<SubscriptionResponse> findSubscriptions(Long id) {
        List<Subscription> subscriptions = subscriptionInternalService.findSubscriptions(id);
        return subscriptions.stream()
            .map(SubscriptionConverter::toSubscriptionResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void cancelSubscription(Long subscribedId, Long userId) {
        subscriptionInternalService.cancelSubscription(subscribedId, userId);
    }
}