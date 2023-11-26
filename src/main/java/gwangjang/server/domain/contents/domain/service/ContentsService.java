package gwangjang.server.domain.contents.domain.service;
import gwangjang.server.domain.contents.application.dto.res.ContentsRes;
import gwangjang.server.domain.contents.application.dto.res.ContentsWithLikeCount;
import gwangjang.server.domain.contents.domain.entity.constant.ApiType;
import gwangjang.server.global.annotation.DomainService;
import reactor.core.publisher.Mono;

import java.util.List;

@DomainService
public interface ContentsService {
    public Mono<Void> saveYoutubeContent(String[] search);
    public List<ContentsRes> getContents(ApiType type);
    List<ContentsRes> getContentsTitle(String issue, ApiType type);
    List<ContentsRes> getKeywordAndType(String Keyword, ApiType apiType);
    ContentsRes getContentsById(Integer contentsId);
    List<ContentsWithLikeCount> getContentLikeCount();
    List<ContentsRes> findContentsByLoginId(String loginId);

}

