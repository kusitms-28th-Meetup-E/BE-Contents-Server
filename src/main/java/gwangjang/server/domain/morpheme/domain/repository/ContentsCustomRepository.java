package gwangjang.server.domain.morpheme.domain.repository;

import gwangjang.server.domain.morpheme.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.morpheme.domain.entity.Contents;

import java.util.List;

public interface ContentsCustomRepository {
    List<ContentsDataRes> getContentsByIssueId(String issue);
    List<Contents> findAllOrderByLikeCountDesc();

}
