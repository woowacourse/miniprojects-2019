package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.relation.RelationResponseDto;
import com.woowacourse.sunbook.domain.relation.Relation;
import com.woowacourse.sunbook.domain.relation.RelationRepository;
import com.woowacourse.sunbook.domain.relation.Relationship;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.relation.exception.RelationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelationService {
	private final RelationRepository relationRepository;
	private final UserService userService;

	@Autowired
	public RelationService(final RelationRepository relationRepository, final UserService userService) {
		this.relationRepository = relationRepository;
		this.userService = userService;
	}

	@Transactional
	public RelationResponseDto addFriend(final Long fromId, final Long toId) {
		User from = userService.findById(fromId);
		User to = userService.findById(toId);

		Relation fromRelation = relationRepository.findByFromAndTo(from, to)
				.orElse(new Relation(from, to));
		Relation toRelation = relationRepository.findByFromAndTo(to, from)
				.orElse(new Relation(to, from));

		fromRelation.addFriend();
		toRelation.requestedFriend();

		relationRepository.save(fromRelation);
		relationRepository.save(toRelation);

		return new RelationResponseDto(fromRelation);
	}

	@Transactional
	public RelationResponseDto beFriend(final Long fromId, final Long toId) {
		User from = userService.findById(fromId);
		User to = userService.findById(toId);

		Relation fromRelation = relationRepository.findByFromAndTo(from, to)
				.orElseThrow(() -> new RelationException(""));
		Relation toRelation = relationRepository.findByFromAndTo(to, from)
				.orElseThrow(() -> new RelationException(""));

		fromRelation.beFriend();
		toRelation.beFriend();

		return new RelationResponseDto(toRelation);
	}

	@Transactional
	public RelationResponseDto delete(final Long fromId, final Long toId) {
		User from = userService.findById(fromId);
		User to = userService.findById(toId);

		Relation fromRelation = relationRepository.findByFromAndTo(from, to)
				.orElseThrow(() -> new RelationException(""));
		Relation toRelation = relationRepository.findByFromAndTo(to, from)
				.orElseThrow(() -> new RelationException(""));

		fromRelation.removeRelationShip();
		toRelation.removeRelationShip();

		return new RelationResponseDto(toRelation);
	}

	@Transactional(readOnly = true)
	public RelationResponseDto getRelationShip(final Long fromId, final Long toId) {
		User from = userService.findById(fromId);
		User to = userService.findById(toId);

		return new RelationResponseDto(
				relationRepository.findByFromAndTo(from, to)
				.orElse(new Relation(from, to))
		);
	}

	@Transactional(readOnly = true)
	public List<RelationResponseDto> getFriends(Long userId) {
		User user = userService.findById(userId);

		return Collections.unmodifiableList(
				getFriendsRelation(user).stream()
						.map(RelationResponseDto::new)
						.collect(Collectors.toList())
		);
	}

	protected List<Relation> getFriendsRelation(User user) {
		return relationRepository.findAllByFromAndRelationship(user, Relationship.FRIEND);
	}

	@Transactional(readOnly = true)
	public List<RelationResponseDto> getRequestedFriends(Long userId) {
		User user = userService.findById(userId);

		return Collections.unmodifiableList(
				getRequestedFriendsRelation(user).stream()
						.map(RelationResponseDto::new)
						.collect(Collectors.toList())
		);
	}

	private List<Relation> getRequestedFriendsRelation(User user) {
		return relationRepository.findAllByFromAndRelationship(user, Relationship.REQUESTED);
	}

	protected boolean isFriend(Long toId, Long fromId) {
		User toUser = userService.findById(toId);
		User fromUser = userService.findById(fromId);
		return relationRepository.existsByFromAndToAndRelationship(toUser, fromUser, Relationship.FRIEND);
	}
}
