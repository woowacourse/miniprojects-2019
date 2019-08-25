package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.MockStorage;
import com.woowacourse.sunbook.application.dto.relation.RelationResponseDto;
import com.woowacourse.sunbook.domain.relation.Relation;
import com.woowacourse.sunbook.domain.relation.Relationship;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class RelationServiceTest extends MockStorage {

	@InjectMocks
	RelationService relationService;

	Relation toRelation;

	User to;

	@BeforeEach
	void setUp() {
		to = new User(new UserEmail("ddu0422@naver.com"), new UserPassword("asdf1234!A"), new UserName("미르"));

		toRelation = new Relation(to, from);
	}

	@Test
	void 친구_신청() {
		given(userService.findById(1L)).willReturn(from);
		given(userService.findById(2L)).willReturn(to);
		given(relationRepository.findByFromAndTo(from, to)).willReturn(Optional.of(fromRelation));
		given(relationRepository.findByFromAndTo(to, from)).willReturn(Optional.of(toRelation));
		given(relationRepository.save(fromRelation)).willReturn(fromRelation);
		given(relationRepository.save(toRelation)).willReturn(toRelation);

		assertThat(relationService.addFriend(1L, 2L)).isEqualTo(Relationship.REQUESTED);
	}

	@Test
	void 친구_수락() {
		given(userService.findById(1L)).willReturn(from);
		given(userService.findById(2L)).willReturn(to);
		given(relationRepository.findByFromAndTo(from, to)).willReturn(Optional.of(fromRelation));
		given(relationRepository.findByFromAndTo(to, from)).willReturn(Optional.of(toRelation));

		toRelation.requestedFriend();

		assertThat(relationService.beFriend(1L, 2L)).isEqualTo(Relationship.FRIEND);
	}

	@Test
	void 관계_끊기() {
		given(userService.findById(1L)).willReturn(from);
		given(userService.findById(2L)).willReturn(to);
		given(relationRepository.findByFromAndTo(from, to)).willReturn(Optional.of(fromRelation));
		given(relationRepository.findByFromAndTo(to, from)).willReturn(Optional.of(toRelation));

		toRelation.requestedFriend();

		assertThat(relationService.delete(1L, 2L)).isEqualTo(Relationship.NONE);
	}

	@Test
	void 두_유저_관계() {
		given(userService.findById(1L)).willReturn(from);
		given(userService.findById(2L)).willReturn(to);
		given(relationRepository.findByFromAndTo(from, to)).willReturn(Optional.of(toRelation));

		assertThat(relationService.getRelationShip(1L, 2L)).isEqualTo(Relationship.NONE);
	}

	@Test
	void 친구들_불러오기() {
		given(userService.findById(1L)).willReturn(from);
		given(relationRepository.findAllByFromAndRelationship(from, Relationship.FRIEND)).willReturn(Arrays.asList(toRelation));

		assertThat(relationService.getFriends(1L)).isEqualTo(Arrays.asList(new RelationResponseDto(toRelation)));
	}

	@Test
	void 친구_신청_유저들_불러오기() {
		given(userService.findById(1L)).willReturn(from);
		given(relationRepository.findAllByFromAndRelationship(from, Relationship.REQUESTED)).willReturn(Arrays.asList(toRelation));

		assertThat(relationService.getRequestedFriends(1L)).isEqualTo(Arrays.asList(new RelationResponseDto(toRelation)));
	}

}