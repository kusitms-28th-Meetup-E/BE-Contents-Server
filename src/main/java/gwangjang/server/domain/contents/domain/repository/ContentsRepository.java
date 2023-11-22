package gwangjang.server.domain.contents.domain.repository;

import gwangjang.server.domain.contents.application.dto.res.BubbleChartRes;
import gwangjang.server.domain.contents.domain.entity.Contents;
import gwangjang.server.domain.contents.domain.entity.constant.ApiType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Integer>,ContentsCustomRepository{

    List<Contents> findByType(ApiType type);
    List<Contents> findByIssueTitleLike(String issue);
    List<Contents> findByKeywordLikeAndTypeOrderByPubDateDesc(String issueTitle, ApiType type);

    @Query(
            value = "SELECT issue_title, keyword, MAX(month) AS max_occurrence_date, MAX(occurrences) AS max_occurrences\n" +
                    "FROM (\n" +
                    "    SELECT issue_title, keyword, SUBSTRING(pub_date, 1, 7) AS month, COUNT(*) AS occurrences\n" +
                    "    FROM contents\n" +
                    "    WHERE TYPE = 'YOUTUBE'\n" +
                    "    GROUP BY issue_title, keyword, month\n" +
                    ") AS subquery\n" +
                    "GROUP BY issue_title, keyword\n" +
                    "ORDER BY max_occurrences DESC",
            nativeQuery = true)
    List<Object[]> findMaxOccurrencesByIssueAndKeyword();


}
