package gwangjang.server.global.feign.client;

import gwangjang.server.domain.morpheme.application.dto.req.TotalReq;
import gwangjang.server.global.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="KEYWORDSERVICE")
public interface FindKeywordFeignClient {
    @GetMapping("/issue/all")
    ResponseEntity<SuccessResponse<List<TotalReq>>> getAll();
}
