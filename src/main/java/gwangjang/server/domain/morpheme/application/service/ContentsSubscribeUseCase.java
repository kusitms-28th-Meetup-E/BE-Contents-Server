package gwangjang.server.domain.morpheme.application.service;

import gwangjang.server.domain.morpheme.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.morpheme.domain.service.ContentsQueryService;
import gwangjang.server.global.feign.client.FindMemberFeignClient;
import gwangjang.server.global.feign.dto.SubscribeIssueFeignRes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentsSubscribeUseCase {

    private final ContentsQueryService contentsQueryService;

    private final FindMemberFeignClient findMemberFeignClient;


    public List<ContentsDataRes> getContentsByIssue(String issue) {

//        List<SubscribeIssueFeignRes> data = findMemberFeignClient.getMySubscribeIssueList().getBody().getData();

        return contentsQueryService.getContentsByIssue(issue);
    }
}
