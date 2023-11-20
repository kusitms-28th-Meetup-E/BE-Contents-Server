package gwangjang.server.domain.like.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gwangjang.server.domain.like.domain.entity.ContentLike;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class LikeRepositoryImpl extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public LikeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ContentLike.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
