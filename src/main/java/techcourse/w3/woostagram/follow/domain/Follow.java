package techcourse.w3.woostagram.follow.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techcourse.w3.woostagram.user.domain.User;

import javax.persistence.*;

@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User from;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User to;
}
