package gwangjang.server.domain.contents.application.service;

import gwangjang.server.domain.contents.application.dto.res.BubbleChartRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.contents.domain.service.ContentsQueryService;
import gwangjang.server.global.feign.client.FindMemberFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        List<BubbleChartRes> list = contentsQueryService.getBubbleChart(issueTitle);
//        List<BubbleChartRes> selectedList = new ArrayList<>();
//        for (int i = 39; i < list.size(); i += 40) {
//            selectedList.add(list.get(i));
//        }
        for(int i=0; i<list.size(); i++){
            list.get(i).setRank((40L*(i+1)));
        }
        return list;
    }
}
