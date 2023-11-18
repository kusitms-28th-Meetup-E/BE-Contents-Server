package gwangjang.server.domain.morpheme.presentation;

import gwangjang.server.domain.morpheme.application.dto.req.TotalReq;
import gwangjang.server.domain.morpheme.domain.service.ContentsService;
import gwangjang.server.global.feign.client.FindKeywordFeignClient;
import gwangjang.server.global.response.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ContentsController {

    private final ContentsService contentsService;
    private final FindKeywordFeignClient findKeywordFeignClient;

    @GetMapping("/keyword/test")
    public ResponseEntity<SuccessResponse<List<TotalReq>>> getKeyword (){
        return findKeywordFeignClient.getAll();
    }
    @Scheduled(cron = "0 0 0 * * *")
    public Mono<Void> analysis() {
        String msg = "이태원 참사";
        return contentsService.saveYoutubeContent(msg);
    }
}
