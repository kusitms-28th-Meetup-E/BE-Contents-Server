package gwangjang.server.domain.morpheme.domain.repository;

import gwangjang.server.domain.morpheme.application.dto.res.ContentsDataRes;

import java.util.List;

public interface ContentsCustomRepository {
    List<ContentsDataRes> getContentsByIssueId(String issue);

}
