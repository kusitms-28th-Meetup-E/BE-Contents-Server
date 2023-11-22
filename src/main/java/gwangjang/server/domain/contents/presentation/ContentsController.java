package gwangjang.server.domain.contents.presentation;

import gwangjang.server.domain.contents.application.dto.res.BubbleChartRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsDataRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsRes;
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
import java.util.List;

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

    @GetMapping("/issueTitle/{issue}")
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentsTitle(@PathVariable String issue) {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContentsTitle(issue)));
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
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentLikeCount() {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.getContentLikeCount()));
    }
    @PostMapping("/my-page/like")
    public ResponseEntity<SuccessResponse<List<ContentsRes>>> getContentsByLoginId(@RequestHeader(value = "user-id") String socialId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(),this.contentsService.findContentsByLoginId(socialId)));
    }

    @GetMapping("/subscribe/{issue}")
    public ResponseEntity<SuccessResponse<List<ContentsDataRes>>> getMySubscribe(@RequestHeader(value = "user-id") String socialId,@PathVariable("issue")String issue) {
        return ResponseEntity.ok(SuccessResponse.create(GET_MY_CONTENTS.getMessage(), this.contentsSubscribeUseCase.getContentsByIssue(issue)));
    }
    @GetMapping("/bubbleChart/{issue}")
    public ResponseEntity<SuccessResponse<List<BubbleChartRes>>> getBubbleChart(@PathVariable("issue")String issue) {
        return ResponseEntity.ok(SuccessResponse.create(GET_CONTENTS_SUCCESS.getMessage(), this.contentsSubscribeUseCase.getBubbleChart(issue)));
    }


}
