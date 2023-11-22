package gwangjang.server.domain.contents.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import gwangjang.server.domain.contents.application.dto.req.TotalReq;
import gwangjang.server.global.annotation.DomainService;
import gwangjang.server.global.feign.client.FindKeywordFeignClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;


@DomainService
@RequiredArgsConstructor
public class ScheduledService {
    private final ContentsService contentsService;
    private final FindKeywordFeignClient findKeywordFeignClient;
    private final NewsAPIService newsAPIService;

    //@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public String getNaverKeyword ()  {
        String b = null;
        List<TotalReq> keywordList = findKeywordFeignClient.getAll().getBody().getData();
        StringBuilder resultStringBuilder = new StringBuilder();
        for (TotalReq issueData : keywordList) {
            String combinedString = issueData.getIssueTitle() + " " + issueData.getKeyword();
            resultStringBuilder.append(combinedString).append("\n");
        }
        String resultString = resultStringBuilder.toString().trim();
        String[] sentences = resultString.split("\n");
        for(String sentence : sentences) {
            newsAPIService.naverAPI(sentence);
        }
        return b;
    }

    //@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public Mono<Void> getYoutubeKeyword () throws JsonProcessingException {
        List<TotalReq> keywordList = findKeywordFeignClient.getAll().getBody().getData();
        StringBuilder resultStringBuilder = new StringBuilder();
        for (TotalReq issueData : keywordList) {
            String combinedString = issueData.getIssueTitle() + " " + issueData.getKeyword();
            resultStringBuilder.append(combinedString).append("\n");
        }
        String resultString = resultStringBuilder.toString().trim();
        String[] sentences = resultString.split("\n");
        System.out.println(sentences);
        Mono<Void> a =contentsService.saveYoutubeContent(sentences);
        return a;
    }
}
