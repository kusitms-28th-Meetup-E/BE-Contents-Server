package gwangjang.server.domain.contents.domain.service;

import gwangjang.server.domain.contents.application.dto.req.TotalReq;
import gwangjang.server.domain.contents.application.dto.res.BubbleChartRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.contents.domain.repository.ContentsRepository;
import gwangjang.server.global.annotation.DomainService;
import gwangjang.server.global.feign.client.FindKeywordFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class ContentsQueryService {
    private final ContentsRepository contentsRepository;
    private final FindKeywordFeignClient findKeywordFeignClient;

    public List<ContentsDataRes> getContentsByIssue(String issue) {
        return contentsRepository.getContentsByIssueId(issue);
    }
    public List<BubbleChartRes> getBubbleChart(String issue) {
        List<TotalReq> keywordList = findKeywordFeignClient.getAll().getBody().getData();
        Map<String, Long> keywordIdMap = keywordList.stream()
                .collect(Collectors.toMap(keyword -> keyword.getIssueTitle() + " " + keyword.getKeyword(), TotalReq::getKeywordId));

        return contentsRepository.findMaxOccurrencesByIssueAndKeyword().stream()
                .filter(objects -> {
                    String issueTitle = (String) objects[0];
                    return issueTitle.contains(issue);
                })
                .map(objects -> {
                    String issueTitle = (String) objects[0];
                    String keyword = (String) objects[1];
                    String date = (String) objects[2];
                    Long count = (Long) objects[3];

                    // Construct the key to look up keywordId in the map
                    String key = issueTitle + keyword;
                    Long keywordId = keywordIdMap.getOrDefault(key, null);

                    return new BubbleChartRes(issueTitle, keyword, date, keywordId != null ? keywordId : count);
                })
                .collect(Collectors.toList());
    }





}
