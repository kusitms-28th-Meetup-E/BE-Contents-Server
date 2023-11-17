package gwangjang.server.domain.morpheme.presentation;

import gwangjang.server.domain.morpheme.domain.service.ContentsService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ContentsController {

    private final ContentsService contentsService;


    @Scheduled(cron = "0 0 0 * * *")
    public Mono<Void> analysis() {
        String msg = "이태원 참사";
        return contentsService.saveYoutubeContent(msg);
    }
}
