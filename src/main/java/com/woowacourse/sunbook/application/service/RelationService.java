package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.domain.friends.Relation;
import com.woowacourse.sunbook.domain.friends.RelationRepository;
import com.woowacourse.sunbook.domain.friends.Relationship;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.validation.exception.RelationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelationService {
	private final RelationRepository relationRepository;
	private final UserService userService;
	private final ModelMapper modelMapper;

	@Autowired
	public RelationService(final RelationRepository relationRepository, final UserService userService, final ModelMapper modelMapper) {
		this.relationRepository = relationRepository;
		this.userService = userService;
		this.modelMapper = modelMapper;
	}

	@Transactional
	public Relationship addFriend(final Long fromId, final Long toId) {
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

		return toRelation.getRelationship();
	}

	@Transactional
	public Relationship beFriend(final Long fromId, final Long toId) {
		User from = userService.findById(fromId);
		User to = userService.findById(toId);

		Relation fromRelation = relationRepository.findByFromAndTo(from, to)
				.orElseThrow(() -> new RelationException(""));
		Relation toRelation = relationRepository.findByFromAndTo(to, from)
				.orElseThrow(() -> new RelationException(""));

		fromRelation.beFriend();
		toRelation.beFriend();

		return toRelation.getRelationship();
	}

	@Transactional
	public Relationship delete(final Long fromId, final Long toId) {
		User from = userService.findById(fromId);
		User to = userService.findById(toId);
		
		Relation fromRelation = relationRepository.findByFromAndTo(from, to)
				.orElseThrow(() -> new RelationException(""));
		Relation toRelation = relationRepository.findByFromAndTo(to, from)
				.orElseThrow(() -> new RelationException(""));

		fromRelation.removeRelationShip();
		toRelation.removeRelationShip();

		return toRelation.getRelationship();
	}

	@Transactional
	public Relationship getRelationShip(final Long fromId, final Long toId) {
		User from = userService.findById(fromId);
		User to = userService.findById(toId);

		return relationRepository.findByFromAndTo(from, to)
				.orElse(new Relation(from, to))
				.getRelationship();
	}

	@Transactional
	public List<UserResponseDto> getRequestedFriendsById(final Long UserId) {
		User from = userService.findById(UserId);
		return getRequestedFriends(from).stream()
				.map(user -> modelMapper.map(user, UserResponseDto.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public List<UserResponseDto> getFriendsById(final Long UserId) {
		User from = userService.findById(UserId);
		return getFriends(from).stream()
				.map(user -> modelMapper.map(user, UserResponseDto.class))
				.collect(Collectors.toList());
	}

	protected List<User> getRequestedFriends(final User user) {
		return getFriendsByRelation(user, Relationship.REQUESTED);
	}

	protected List<User> getFriends(final User user) {
		return getFriendsByRelation(user, Relationship.FRIEND);
	}

	private List<User> getFriendsByRelation(final User user, final Relationship relationship) {
		return relationRepository.findByFromAndRelationship(user, relationship).stream()
				.map(Relation::getTo)
				.collect(Collectors.toList());
	}
}
