package com.woowacourse.sunbook.domain.friends;

import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.domain.validation.exception.RelationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelationTest {
	User user1;

	@Mock
	User user2;

	Relation relation;

	@BeforeEach
	void setUp() {
		user1 = new User(new UserEmail("ddu0422@naver.com"), new UserPassword("asdf1234!A"), new UserName("미르"));
	}

	@Test
	void 생성_테스트() {
		assertDoesNotThrow(() -> new Relation(user1, user2));
	}

	@Test
	void 자기_자신과_관계_생성_오류() {
		assertThrows(RelationException.class, () -> new Relation(user1, user1));
	}

	@Test
	void 친구_요청_테스트() {
		relation = new Relation(user1, user2);

		assertThat(relation.addFriend()).isEqualTo(Relationship.ADD);
	}

	@Test
	void 친구_요청_중복_테스트() {
		relation = new Relation(user1, user2);
		relation.addFriend();
		assertThrows(RelationException.class, () -> relation.addFriend());
	}

	@Test
	void 친구_요청_받음_테스트() {
		relation = new Relation(user1, user2);

		assertThat(relation.requestedFriend()).isEqualTo(Relationship.REQUESTED);
	}

	@Test
	void 친구_요청_받음_중복_테스트() {
		relation = new Relation(user1, user2);

		relation.requestedFriend();

		assertThrows(RelationException.class, () -> relation.requestedFriend());
	}

	@Test
	void 친구됨_테스트() {
		relation = new Relation(user1, user2);

		relation.addFriend();

		assertThat(relation.beFriend()).isEqualTo(Relationship.FRIEND);
	}

	@Test
	void 친구_신청_하지_않고_친구됨_오류_테스트() {
		relation = new Relation(user1, user2);

		assertThrows(RelationException.class, () -> relation.beFriend());
	}

	@Test
	void 관계_끊음_테스트() {
		relation = new Relation(user1, user2);

		relation.addFriend();

		assertThat(relation.removeRelationShip()).isEqualTo(Relationship.NONE);
	}

	@Test
	void 관계_없을_경우_관계_끊음_오류_테스트() {
		relation = new Relation(user1, user2);

		assertThrows(RelationException.class, () -> relation.removeRelationShip());
	}
}