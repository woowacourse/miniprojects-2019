package com.woowacourse.sunbook.application.dto.relation;

import com.woowacourse.sunbook.domain.relation.Relation;
import com.woowacourse.sunbook.domain.relation.Relationship;
import com.woowacourse.sunbook.domain.user.UserName;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id", "userName", "relationship"})
public class RelationResponseDto {
	private Long id;
	private UserName userName;
	private Relationship relationship;

	public RelationResponseDto(Relation relation) {
		this.id = relation.getTo().getId();
		this.userName = relation.getTo().getUserName();
		this.relationship = relation.getRelationship();
	}
}
