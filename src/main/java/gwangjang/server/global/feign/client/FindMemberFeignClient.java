package gwangjang.server.global.feign.client;

import gwangjang.server.global.feign.dto.SubscribeIssueFeignRes;
import gwangjang.server.global.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="MEMBERSERVICE")
public interface FindMemberFeignClient {

    @GetMapping("/subscribe/contents")
    ResponseEntity<SuccessResponse<List<SubscribeIssueFeignRes>>> getMySubscribeIssueList() ;


}



