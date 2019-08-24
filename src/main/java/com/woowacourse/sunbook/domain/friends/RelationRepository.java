package com.woowacourse.sunbook.domain.friends;

import com.woowacourse.sunbook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<Relation, Long> {
	List<Relation> findByFrom(User from);

	List<Relation> findByFromAndRelationship(User user, Relationship relationship);
	
	Optional<Relation> findByFromAndTo(User from, User to);
}
