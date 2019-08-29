package com.woowacourse.edd.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User subscriber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User subscribed;

    private Subscription() {
    }

    public Subscription(User subscriber, User subscribed) {
        this.subscriber = subscriber;
        this.subscribed = subscribed;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public User getSubscribed() {
        return subscribed;
    }
}
