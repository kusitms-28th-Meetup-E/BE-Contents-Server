package gwangjang.server.domain.morpheme.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import gwangjang.server.domain.morpheme.application.dto.req.TotalReq;
import gwangjang.server.domain.morpheme.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.morpheme.application.dto.res.ContentsRes;
import gwangjang.server.domain.morpheme.application.service.ContentsSubscribeUseCase;

import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import gwangjang.server.domain.morpheme.domain.service.ContentsService;
import gwangjang.server.domain.morpheme.domain.service.ImageUrlUpdateService;
import gwangjang.server.domain.morpheme.domain.service.NewsAPIService;
import gwangjang.server.domain.morpheme.presentation.constant.ContentsResponseMessage;
import gwangjang.server.global.feign.client.FindKeywordFeignClient;
import gwangjang.server.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gwangjang.server.domain.morpheme.presentation.constant.ContentsResponse.GET_MY_CONTENTS;

@RestController
@AllArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;
    private final FindKeywordFeignClient findKeywordFeignClient;

    private final ContentsSubscribeUseCase contentsSubscribeUseCase;

    private final NewsAPIService newsAPIService;

    private final ImageUrlUpdateService imageUrlUpdateService;

    @GetMapping("/contents/{type}")
    public  ResponseEntity<SuccessResponse<List<ContentsRes>>> getYoutubeContents(@PathVariable ApiType type) throws NoSuchAlgorithmException, KeyManagementException {
        List<ContentsRes> contents = this.contentsService.getContents(type);
        //imageUrlUpdateService.updateImageUrl(contents);
        return ResponseEntity.ok(SuccessResponse.create(ContentsResponseMessage.GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContents(type)));
    }

    @GetMapping("/issueTitle/{issue}")
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentsTitle(@PathVariable String issue) {
        return ResponseEntity.ok(SuccessResponse.create(ContentsResponseMessage.GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContentsTitle(issue)));
    }

    @GetMapping("/keyword/{keyword}/{type}")
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentsTitle(@PathVariable String keyword, @PathVariable ApiType type) {
        return ResponseEntity.ok(SuccessResponse.create(ContentsResponseMessage.GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getKeywordAndType(keyword,type)));
    }
    @GetMapping("/{contentId}")
    public ResponseEntity<SuccessResponse<ContentsRes>> getContentsTitle(@PathVariable Integer contentId) {
        return ResponseEntity.ok(SuccessResponse.create(ContentsResponseMessage.GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContentsById(contentId)));
    }

    @GetMapping("/naver/contents")
    public ResponseEntity<SuccessResponse<String>> getNaverContents() {
        return ResponseEntity.ok(SuccessResponse.create(ContentsResponseMessage.GET_CONTENTS_SUCCESS.getMessage(),this.newsAPIService.naverAPI("test")));
    }
    @GetMapping("/contents/like")
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentLikeCount() {
        return ResponseEntity.ok(SuccessResponse.create(ContentsResponseMessage.GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContentLikeCount()));
    }
    @PostMapping("/my-page/like")
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentsByLoginId(@RequestHeader(value = "user-id") String socialId) {
        return ResponseEntity.ok(SuccessResponse.create(ContentsResponseMessage.GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.findContentsByLoginId(socialId)));
    }

    //컨텐츠 가져오는 API
    //유튜브에서 제공해주는 할당량 다 사용해서 어떻게 할지 얘기할 것.
    // 진짜 조금 지원해줌 ㅜ

//    @GetMapping("/keyword/test")
//    //@Scheduled(cron = "0 0 0 * * *")
//    public ResponseEntity<SuccessResponse<String>> getKeyword () throws JsonProcessingException {
//        Mono<Void> a = null;
//        String b = null;
//        // 이 부분에서 findKeywordFeignClient.getAll() 호출하고 결과를 받아온다고 가정합니다.
//        List<TotalReq> keywordList = findKeywordFeignClient.getAll().getBody().getData();
//        StringBuilder resultStringBuilder = new StringBuilder();
//
//        for (TotalReq issueData : keywordList) {
//            String combinedString = issueData.getIssueTitle() + " " + issueData.getKeyword();
//            resultStringBuilder.append(combinedString).append("\n");
//        }
//        // 결과 문자열 출력
//        String resultString = resultStringBuilder.toString().trim();
//        // issueId가 같은 경우에 issueTitle과 keyword를 조합하여 msg를 만듭니다.
//        StringBuilder msgBuilder = new StringBuilder();

//        // 최종 결과
//        String msg = msgBuilder.toString();

//        String[] sentences = msg.split("\n");
//        for (String sentence : sentences) {
//            System.out.println(sentence);
//
//        }
//
//        String[] sentences = resultString.split("\n");
//       // a = analysis(sentences);
//        for(String sentence : sentences) {
//           b += newsAPIService.naverAPI(sentence);
//        }
//        //System.out.println(resultString);
//        //contentsService.saveYoutubeContent(sentences);
//
//        return b;
//    }


    @GetMapping("/subscribe/{issue}")
    public ResponseEntity<SuccessResponse<List<ContentsDataRes>>> getMySubscribe(@RequestHeader(value = "user-id") String socialId,@PathVariable("issue")String issue) {
        return ResponseEntity.ok(SuccessResponse.create(GET_MY_CONTENTS.getMessage(), this.contentsSubscribeUseCase.getContentsByIssue(issue)));
    }

}
