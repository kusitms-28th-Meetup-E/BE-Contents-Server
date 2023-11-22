package gwangjang.server.domain.contents.application.service;

import gwangjang.server.domain.contents.application.dto.res.BubbleChartRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.contents.domain.service.ContentsQueryService;
import gwangjang.server.global.feign.client.FindMemberFeignClient;
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

    public List<BubbleChartRes> getBubbleChart(String issueTitle) {
        return contentsQueryService.getBubbleChart(issueTitle);
    }
}
