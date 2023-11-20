package gwangjang.server.domain.morpheme.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gwangjang.server.domain.like.domain.entity.QContentLike;
import gwangjang.server.domain.morpheme.application.dto.res.ContentLikeCountRes;
import gwangjang.server.domain.morpheme.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.entity.QContents;
import jakarta.persistence.EntityManager;

import java.util.List;

import static gwangjang.server.domain.morpheme.domain.entity.QContents.contents;


public class ContentsCustomRepositoryImpl implements ContentsCustomRepository {


    private final JPAQueryFactory queryFactory;

    public ContentsCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ContentsDataRes> getContentsByIssueId(String issue) {
        return queryFactory.select(Projections.constructor(ContentsDataRes.class,
                        contents.topic,
                        contents.issueTitle,
                        contents.keyword,
                        contents.type.stringValue(),
                        contents.title,
                        contents.description
                ))
                .from(contents)
                .where(contents.issueTitle.eq(issue))
                .limit(10).fetch();
    }

    public List<Contents> findAllOrderByLikeCountDesc() {
        QContents contents = QContents.contents;
        QContentLike contentLike = QContentLike.contentLike;

        List<Contents> result = queryFactory
                .select(contents)
                .from(contents)
                .leftJoin(contentLike).on(contents.contents_id.eq(contentLike.contents.contents_id))
                .groupBy(contents.contents_id)
                .orderBy(contentLike.likeId.count().desc())
                .fetch();
        return result;
    }

}

