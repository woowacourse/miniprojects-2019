package com.wootecobook.turkey.friend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendAskRepository extends JpaRepository<FriendAsk, Long> {

    List<FriendAsk> findAllByReceiverId(Long receiverId);

    Optional<FriendAsk> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    void deleteBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
