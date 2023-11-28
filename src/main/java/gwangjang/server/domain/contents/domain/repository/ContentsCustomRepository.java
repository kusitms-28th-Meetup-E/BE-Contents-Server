package gwangjang.server.domain.contents.domain.repository;

import gwangjang.server.domain.contents.application.dto.res.BubbleChartRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsWithLikeCountRes;
import gwangjang.server.domain.contents.domain.entity.Contents;
import gwangjang.server.domain.contents.domain.entity.constant.ApiType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentsCustomRepository {
    List<ContentsDataRes> getContentsByIssueId(String issue);
    List<Contents> findAllOrderByLikeCountDesc();
    List<Contents> findContentsByLoginId(String loginId);
    void updateContentsImageUrl(Integer contentsId, String newImageUrl);
    List<ContentsWithLikeCountRes> getContentsWithLikeCount(String userId, ApiType type);
    List<ContentsWithLikeCountRes> getContentsKeyword(String userId, ApiType type, String keyword);
    List<ContentsWithLikeCountRes> getContentsIssue(String userId, ApiType type, String keyword);

}