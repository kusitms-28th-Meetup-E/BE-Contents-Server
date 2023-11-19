package gwangjang.server.domain.morpheme.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import gwangjang.server.domain.morpheme.application.dto.req.TotalReq;
import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.service.ContentsService;
import gwangjang.server.domain.morpheme.domain.service.NewsAPIService;
import gwangjang.server.global.feign.client.FindKeywordFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ContentsController {

    private final ContentsService contentsService;
    private final FindKeywordFeignClient findKeywordFeignClient;

    private final NewsAPIService newsAPIService;

    @GetMapping("/keyword/test")
    //@Scheduled(cron = "0 0 0 * * *")
    public String getKeyword () throws JsonProcessingException {
        Mono<Void> a = null;
        String b = null;
        // 이 부분에서 findKeywordFeignClient.getAll() 호출하고 결과를 받아온다고 가정합니다.
        List<TotalReq> keywordList = findKeywordFeignClient.getAll().getBody().getData();
        StringBuilder resultStringBuilder = new StringBuilder();

        for (TotalReq issueData : keywordList) {
            String combinedString = issueData.getIssueTitle() + " " + issueData.getKeyword();
            resultStringBuilder.append(combinedString).append("\n");
        }
        // 결과 문자열 출력
        String resultString = resultStringBuilder.toString().trim();

//        // issueId가 같은 경우에 issueTitle과 keyword를 조합하여 msg를 만듭니다.
//        StringBuilder msgBuilder = new StringBuilder();
//        Long currentIssueId = -1L;  // 초기값으로 사용되지 않을 값으로 설정
//        String currentIssueTitle = null;
//        StringBuilder currentKeywords = new StringBuilder();
//
//        for (TotalReq keyword : keywordList) {
//            if (keyword.getIssueId() != currentIssueId) {
//                // 새로운 issueId에 대한 처리
//                if (currentIssueId != -1) {
//                    // 처음이 아니면 이전 데이터를 msg에 추가
//                    msgBuilder.append(currentIssueTitle).append(" ").append(currentKeywords).append("\n");
//                }
//
//                // 새로운 issueId 설정
//                currentIssueId = keyword.getIssueId();
//                currentIssueTitle = keyword.getIssueTitle();
//                currentKeywords = new StringBuilder();
//            }
//
//            // keyword 추가
//            currentKeywords.append(keyword.getKeyword()).append(" ");
//        }
//
//        // 마지막 데이터 처리
//        if (currentIssueId != -1) {
//            msgBuilder.append(currentIssueTitle).append(" ").append(currentKeywords).append("\n");
//        }
//
//        // 최종 결과인 msg를 얻습니다.
//        String msg = msgBuilder.toString();
//        System.out.println(msg);
//        // 이후에 msg를 사용하면 됩니다.
//        String[] sentences = msg.split("\n");
//        for (String sentence : sentences) {
//            System.out.println(sentence);
//
//        }

        String[] sentences = resultString.split("\n");
       // a = analysis(sentences);
        for(String sentence : sentences) {
           b += newsAPIService.naverAPI(sentence);
        }
        //System.out.println(resultString);
        //contentsService.saveYoutubeContent(sentences);

        return b;
    }

//    @GetMapping("/test")
    public Mono<Void> analysis(String[] issue) {
        return contentsService.saveYoutubeContent(issue);
    }

    @GetMapping("/youtube/contents")
    public  List<Contents> getYoutubeContents(){
        return contentsService.getYoutubeContents();
    }


    @GetMapping("/naver/contents")
    public String getNaverContents() throws JsonProcessingException {
        return newsAPIService.naverAPI("test");
    }
}
