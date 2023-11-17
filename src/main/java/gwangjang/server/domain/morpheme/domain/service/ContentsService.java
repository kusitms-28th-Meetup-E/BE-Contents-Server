package gwangjang.server.domain.morpheme.domain.service;
import gwangjang.server.global.annotation.DomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@DomainService
public interface ContentsService {
    public Mono<Void> saveYoutubeContent(String search);

}

