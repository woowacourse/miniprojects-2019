package com.wootecobook.turkey.friend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByRelatingUserId(Long id);

    Optional<Friend> findByRelatingUserIdAndRelatedUserId(Long relatingUserId, Long relatedUserId);

    void deleteByRelatedUserIdOrRelatingUserId(Long relatedUserId, Long relatingUserId);
}
