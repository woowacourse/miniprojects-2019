package techcourse.w3.woostagram.follow.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techcourse.w3.woostagram.common.support.BaseEntity;
import techcourse.w3.woostagram.user.domain.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public class Follow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User from;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User to;

    @Builder
    public Follow(User from, User to) {
        this.from = from;
        this.to = to;
    }

    public void nullify() {
        from = null;
        to = null;
    }
}