package com.woowacourse.edd.application.converter;

import com.woowacourse.edd.application.response.SubscriptionCountResponse;
import com.woowacourse.edd.application.response.SubscriptionResponse;
import com.woowacourse.edd.domain.Subscription;

public class SubscriptionConverter {

    public static SubscriptionCountResponse toResponse(int count) {
        return new SubscriptionCountResponse(count);
    }

    public static SubscriptionResponse toSubscriptionResponse(Subscription subscription) {
        return new SubscriptionResponse(subscription.getSubscribed().getId(), subscription.getSubscribed().getName());
    }
}
