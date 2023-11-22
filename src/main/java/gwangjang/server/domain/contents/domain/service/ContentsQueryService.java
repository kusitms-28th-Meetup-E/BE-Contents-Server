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
import java.util.Comparator;
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

    public List<BubbleChartRes> getBubbleChart(String issueTitle) {
        List<TotalReq> keywordList = findKeywordFeignClient.getAll().getBody().getData();
        Map<String, Long> keywordIdMap = keywordList.stream()
                .collect(Collectors.toMap(keyword -> keyword.getIssueTitle() + " " + keyword.getKeyword(), TotalReq::getKeywordId));

        List<BubbleChartRes> bubbleChartList = contentsRepository.findMaxOccurrencesByIssueAndKeyword().stream()
                .filter(objects -> {
                    String fetchedIssueTitle = (String) objects[0];
                    return fetchedIssueTitle.contains(issueTitle);
                })
                .map(objects -> {
                    String fetchedIssueTitle = (String) objects[0];
                    String keyword = (String) objects[1];
                    String date = ((String) objects[2]).substring(5, 7);  // Keep only year and month part
                    Long count = (Long) objects[3];

                    // Construct the key to look up keywordId in the map
                    String key = fetchedIssueTitle + keyword;
                    Long keywordId = keywordIdMap.getOrDefault(key, null);

                    return new BubbleChartRes(fetchedIssueTitle, keyword, date, keywordId != null ? keywordId : count);
                })
                .collect(Collectors.toList());

        // Sort the list based on rank (assuming 'rank' is a field in BubbleChartRes)
        bubbleChartList.sort(Comparator.comparing(BubbleChartRes::getRank));

        return bubbleChartList;
    }
}
