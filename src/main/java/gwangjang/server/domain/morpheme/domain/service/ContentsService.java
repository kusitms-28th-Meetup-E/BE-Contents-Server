package gwangjang.server.domain.morpheme.domain.service;
import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import gwangjang.server.global.annotation.DomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@DomainService
public interface ContentsService {
    public Mono<Void> saveYoutubeContent(String[] search);
    public List<Contents> getContents(ApiType type);
    List<Contents> getContentsTitle(String issue);
    List<Contents> getKeywordAndType(String Keyword, ApiType apiType);
}

