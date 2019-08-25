package com.woowacourse.sunbook.domain.relation;

import com.woowacourse.sunbook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<Relation, Long> {
	Optional<Relation> findByFromAndTo(User from, User to);

	List<Relation> findAllByFromAndRelationship(User from, Relationship relationship);
}
