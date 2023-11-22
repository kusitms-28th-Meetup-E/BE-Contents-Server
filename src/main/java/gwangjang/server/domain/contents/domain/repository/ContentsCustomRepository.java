package gwangjang.server.domain.contents.domain.repository;

import gwangjang.server.domain.contents.application.dto.res.BubbleChartRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.contents.domain.entity.Contents;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentsCustomRepository {
    List<ContentsDataRes> getContentsByIssueId(String issue);
    List<Contents> findAllOrderByLikeCountDesc();
    List<Contents> findContentsByLoginId(String loginId);
    void updateContentsImageUrl(Integer contentsId, String newImageUrl);

}
