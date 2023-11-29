package gwangjang.server.domain.contents.presentation;

import gwangjang.server.domain.contents.application.dto.res.*;
import gwangjang.server.domain.contents.application.service.ContentsSubscribeUseCase;

import gwangjang.server.domain.contents.domain.entity.constant.ApiType;
import gwangjang.server.domain.contents.domain.service.ContentsService;
import gwangjang.server.domain.contents.domain.service.ImageUrlUpdateService;
import gwangjang.server.domain.contents.domain.service.NewsAPIService;
import gwangjang.server.domain.contents.presentation.constant.ContentsResponseMessage;
import gwangjang.server.global.feign.client.FindKeywordFeignClient;
import gwangjang.server.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static gwangjang.server.domain.contents.presentation.constant.ContentsResponse.GET_MY_CONTENTS;
import static gwangjang.server.domain.contents.presentation.constant.ContentsResponseMessage.GET_CONTENTS_SUCCESS;

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
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContents(type)));
    }

    @GetMapping("/issueTitle/{issue}/{type}")
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentsTitleAndType(@PathVariable String issue, @PathVariable ApiType type) {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContentsTitle(issue,type)));
    }

    @GetMapping("/keyword/{keyword}/{type}")
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentsTitle(@PathVariable String keyword, @PathVariable ApiType type) {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getKeywordAndType(keyword,type)));
    }
    @GetMapping("/{contentId}")
    public ResponseEntity<SuccessResponse<ContentsRes>> getContentsId(@PathVariable Integer contentId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContentsById(contentId)));
    }

    @GetMapping("/naver/contents")
    public ResponseEntity<SuccessResponse<String>> getNaverContents() {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.newsAPIService.naverAPI("test")));
    }
    @GetMapping("/contents/like")
    public ResponseEntity<SuccessResponse<List<ContentsWithLikeCount>>> getContentLikeCount() {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContentLikeCount()));
    }
    @PostMapping("/my-page/like")
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentsByLoginId(@RequestHeader(value = "user-id") String socialId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.findContentsByLoginId(socialId)));
    }

    @GetMapping("/subscribe/{issue}")
    public ResponseEntity<SuccessResponse<List<ContentsDataRes>>> getMySubscribe(@RequestHeader(value = "user-id") String socialId, @PathVariable("issue")String issue) {
        return ResponseEntity.ok(SuccessResponse.create(GET_MY_CONTENTS.getMessage(), this.contentsSubscribeUseCase.getContentsByIssue(issue)));
    }
    @GetMapping("/bubbleChart/{issue}")
    public ResponseEntity<SuccessResponse<List<BubbleFrontRes>>> getBubbleChart(@PathVariable("issue") String issue) {
        List<BubbleChartRes> bubbleChartList = this.contentsSubscribeUseCase.getBubbleChart(issue);
        List<BubbleFrontRes> result = new ArrayList<>();
        Random rand = new Random();

        result.add(new BubbleFrontRes("", 6, 0, 0));

        Set<Integer> usedRandomValues = new HashSet<>(); // to track used random values

        for (BubbleChartRes bubbleChart : bubbleChartList) {
            int x = Integer.parseInt(bubbleChart.getDate());
            Integer y;

            // Generate a unique random value
            do {
                y = rand.nextInt(10) + 1;
            } while (!usedRandomValues.add(y));

            int z = Math.toIntExact(bubbleChart.getRank());
            String name = bubbleChart.getKeyword();

            result.add(new BubbleFrontRes(name,x, y, z));
        }

// Add element at the end with values from the last index
        if (!bubbleChartList.isEmpty()) {
            BubbleChartRes lastBubbleChart = bubbleChartList.get(bubbleChartList.size() - 1);
            result.add(new BubbleFrontRes("",12, 0, 0 ));
        }

        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(), result));

    }
    @GetMapping("/contents/all/like/{type}")
    public ResponseEntity<SuccessResponse<List<ContentsWithLikeCountRes>>> getContentsWithLikeCount(@RequestHeader(value = "user-id") String socialId,@PathVariable("type") ApiType type) {
        String userId = "testId";
        System.out.println(type);
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(), this.contentsService.getContentsWithLikeCount(socialId,type)));
    }//@RequestHeader(value = "user-id") String socialId

    @GetMapping("/contents/keyword/{keyword}/{type}")
    public ResponseEntity<SuccessResponse<List<ContentsWithLikeCountRes>>> getContentsKeyword(@RequestHeader(value = "user-id") String socialId,@PathVariable("keyword") String keyword,@PathVariable("type") ApiType type) {
        String userId = "testId";
        System.out.println(type);
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(), this.contentsService.getContentsKeyword(  socialId, type,  keyword)));
    }//@RequestHeader(value = "user-id") String socialId
    @GetMapping("/contents/issue/{issue}/{type}")
    public ResponseEntity<SuccessResponse<List<ContentsWithLikeCountRes>>> getContentsIssue(@RequestHeader(value = "user-id") String socialId,@PathVariable("issue") String issue,@PathVariable("type") ApiType type) {
        String userId = "testId";
        System.out.println(type);
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(), this.contentsService.getContentsIssue(  socialId, type,  issue)));
    }//@RequestHeader(value = "user-id") String socialId
}
