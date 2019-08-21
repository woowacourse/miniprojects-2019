package com.wootecobook.turkey.friend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByRelatingUserId(Long id);
}
