package com.woowacourse.sunbook.domain.friends;

import com.woowacourse.sunbook.domain.BaseEntity;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.validation.exception.RelationException;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@ToString
public class Relation extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "proposer_id", foreignKey = @ForeignKey(name ="proposer_id"))
	private User from;

	@ManyToOne
	@JoinColumn(name = "target_id", foreignKey = @ForeignKey(name = "target_id"))
	private User to;

	@Column(nullable = false)
	private Relationship relationship;

	public Relation(User from, User to) {
		validRelation(from, to);
		this.from = from;
		this.to = to;
		this.relationship = Relationship.NONE;
	}

	private void validRelation(User from, User to) {
		if (from.equals(to)) {
			throw new RelationException("자신은 친구 추가할 수 없습니다");
		}
	}

	public Relationship addFriend() {
		if (relationship.isPossibleAddOrRequested()) {
			relationship = Relationship.ADD;

			return relationship;
		}

		throw new RelationException("이미 신청했습니다");
	}

	public Relationship requestedFriend() {
		if (relationship.isPossibleAddOrRequested()) {
			relationship = Relationship.REQUESTED;

			return relationship;
		}

		throw new RelationException("이미 신청했습니다");
	}

	public Relationship beFriend() {
		if (relationship.isPossibleBeFriend()) {
			relationship = Relationship.FRIEND;

			return relationship;
		}

		throw new RelationException("친구 신청 먼저 해주세요");
	}

	public Relationship removeRelationShip() {
		if (relationship.isPossibleRemove()) {
			relationship = Relationship.NONE;

			return relationship;
		}

		throw new RelationException("");
	}

}

