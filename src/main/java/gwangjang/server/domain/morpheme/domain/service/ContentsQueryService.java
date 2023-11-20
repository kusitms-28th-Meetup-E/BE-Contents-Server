package gwangjang.server.domain.morpheme.domain.service;

import gwangjang.server.domain.morpheme.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.morpheme.domain.repository.ContentsRepository;
import gwangjang.server.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class ContentsQueryService {
    private final ContentsRepository contentsRepository;

    public List<ContentsDataRes> getContentsByIssue(String issue) {
        return contentsRepository.getContentsByIssueId(issue);
    }


}
