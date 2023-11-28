package gwangjang.server.domain.contents.domain.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gwangjang.server.domain.contents.application.dto.res.BubbleChartRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsWithLikeCountRes;
import gwangjang.server.domain.contents.application.dto.res.*;
import gwangjang.server.domain.contents.domain.entity.constant.ApiType;
import gwangjang.server.domain.like.domain.entity.QContentLike;
import gwangjang.server.domain.contents.domain.entity.Contents;
import gwangjang.server.domain.contents.domain.entity.QContents;
import jakarta.persistence.EntityManager;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.core.types.dsl.StringExpression;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.Projections.fields;
import static gwangjang.server.domain.contents.domain.entity.QContents.contents;


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

    public List<ContentsWithLikeCount> findAllOrderByLikeCountDesc() {
        QContents contents = QContents.contents;
        QContentLike contentLike = QContentLike.contentLike;

        List<ContentsWithLikeCount> result = queryFactory
                .select(Projections.constructor(
                        ContentsWithLikeCount.class,
                        contents,
                        contentLike.likeId.count().as("likeCount")
                ))
                .from(contents)
                .leftJoin(contentLike).on(contents.contents_id.eq(contentLike.contents.contents_id))
                .groupBy(contents.contents_id)
                .orderBy(contentLike.likeId.count().desc())
                .fetch();
        return result;
    }
    public List<Contents> findContentsByLoginId(String loginId) {
        QContents contents = QContents.contents;
        QContentLike contentLike = QContentLike.contentLike;


        List<Integer> likedContentIds = queryFactory
                .select(contentLike.contents.contents_id)
                .from(contentLike)
                .where(contentLike.loginId.eq(loginId))
                .fetch();

        if (likedContentIds.isEmpty()) {
            return Collections.emptyList(); // 만약 좋아요한 컨텐츠가 없다면 빈 리스트 반환
        }

        List<Contents> result = queryFactory
                .select(contents)
                .from(contents)
                .where(contents.contents_id.in(likedContentIds))
                .fetch();

        return result;
    }

    public void updateContentsImageUrl(Integer contentsId, String newImageUrl) {
        queryFactory
                .update(contents)
                .set(contents.imgUrl, newImageUrl)
                .where(contents.contents_id.eq(contentsId))
                .execute();
    }

    public List<ContentsWithLikeCountRes> getContentsWithLikeCount(String userId, ApiType type) {
        QContents contents = QContents.contents;
        QContentLike contentLike = QContentLike.contentLike;

        return queryFactory
                .select(Projections.constructor(ContentsWithLikeCountRes.class,
                        contents.contents_id,
                        contents.url,
                        contents.title,
                        contents.description,
                        contents.type,
                        contents.issueTitle,
                        contents.keyword,
                        contents.pubDate,
                        contents.topic,
                        contents.imgUrl,
                        contentLike.likeId.countDistinct().as("likeCount"),
                        Expressions.booleanTemplate("coalesce((select 1 from ContentLike cl where cl.contents = {0} and cl.loginId = {1}), 0)",
                                contents, userId).as("userLiked")))
                .from(contents)
                .where(contents.type.eq(type))
                .leftJoin(contentLike).on(contentLike.contents.eq(contents))
                .groupBy(contents.contents_id)
                .orderBy(contentLike.likeId.count().desc())
                .fetch();
    }
    public List<ContentsWithLikeCountRes> getContentsKeyword(String userId, ApiType type, String keyword) {
        QContents contents = QContents.contents;
        QContentLike contentLike = QContentLike.contentLike;

        return queryFactory
                .select(Projections.constructor(ContentsWithLikeCountRes.class,
                        contents.contents_id,
                        contents.url,
                        contents.title,
                        contents.description,
                        contents.type,
                        contents.issueTitle,
                        contents.keyword,
                        contents.pubDate,
                        contents.topic,
                        contents.imgUrl,
                        contentLike.likeId.countDistinct().as("likeCount"),
                        Expressions.booleanTemplate("coalesce((select 1 from ContentLike cl where cl.contents = {0} and cl.loginId = {1}), 0)",
                                contents, userId).as("userLiked")))
                .from(contents)
                .leftJoin(contentLike).on(contentLike.contents.eq(contents))
                .where(contents.type.eq(type) // Add the type parameter
                        .and(contents.keyword.contains(keyword))) // Use contains for keyword
                .groupBy(contents.contents_id)
                .orderBy(contentLike.likeId.count().desc())
                .fetch();
    }

    public List<ContentsWithLikeCountRes> getContentsIssue(String userId, ApiType type, String issue) {
        QContents contents = QContents.contents;
        QContentLike contentLike = QContentLike.contentLike;

        return queryFactory
                .select(Projections.constructor(ContentsWithLikeCountRes.class,
                        contents.contents_id,
                        contents.url,
                        contents.title,
                        contents.description,
                        contents.type,
                        contents.issueTitle,
                        contents.keyword,
                        contents.pubDate,
                        contents.topic,
                        contents.imgUrl,
                        contentLike.likeId.countDistinct().as("likeCount"),
                        Expressions.booleanTemplate("coalesce((select 1 from ContentLike cl where cl.contents = {0} and cl.loginId = {1}), 0)",
                                contents, userId).as("userLiked")))
                .from(contents)
                .leftJoin(contentLike).on(contentLike.contents.eq(contents))
                .where(contents.type.eq(type) // Add the type parameter
                        .and(contents.issueTitle.contains(issue))) // Use contains for keyword
                .groupBy(contents.contents_id)
                .orderBy(contentLike.likeId.count().desc())
                .fetch();
    }

//    public List<ContentsLikeCount> searchContentsWithLikeCount(String keyword, ApiType type, String loginId) {
//        QContents contents = QContents.contents;
//        QContentLike contentLike = QContentLike.contentLike;
//
//        List<Tuple> result = queryFactory
//                .select(
//                        contents.contents_id,
//                        contents.url,
//                        contents.title,
//                        contents.description,
//                        contents.type,
//                        contents.issueTitle,
//                        contents.keyword,
//                        contents.pubDate,
//                        contents.topic,
//                        contents.imgUrl,
//                        contentLike.likeId.count().coalesce(0L).as("likeCount"),
//                        contentLike.likeId.count().coalesce(0L)
//                                .as("userLiked")
//                )
//                .from(contents)
//                .leftJoin(contentLike).on(contents.contents_id.eq(contentLike.contents.contents_id).and(contentLike.loginId.eq(loginId)))
//                .where(contents.keyword.containsIgnoreCase(keyword).and(contents.type.eq(type)))
//                .groupBy(contents.contents_id, contents.url, contents.title, contents.description, contents.type, contents.issueTitle,
//                        contents.keyword, contents.pubDate, contents.topic, contents.imgUrl)
//                .fetch();
//
//        return result.stream()
//                .map(tuple -> new ContentsLikeCount(
//                        tuple.get(contents.contents_id),
//                        tuple.get(contents.url),
//                        tuple.get(contents.title),
//                        tuple.get(contents.description),
//                        tuple.get(contents.type),
//                        tuple.get(contents.issueTitle),
//                        tuple.get(contents.keyword),
//                        tuple.get(contents.pubDate),
//                        tuple.get(contents.topic),
//                        tuple.get(contents.imgUrl),
//                        tuple.get("likeCount", Integer.class), // Change to Integer.class
//                        tuple.get("userLiked", Boolean.class)
//                ))
//                .collect(Collectors.toList());
//
//    }
}

