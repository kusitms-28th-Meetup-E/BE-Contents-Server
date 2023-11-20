package gwangjang.server.domain.morpheme.application.dto.res;

import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentsRes {
    private Integer contents_id;
    private String url;
    private String title;
    private String description;
    ApiType type;
    private String issueTitle;
    private String keyword;
    private String pubDate;

    public static Contents toEntity(ContentsRes contentsRes) {
        return new Contents(
                contentsRes.getUrl(),
                contentsRes.getTitle(),
                contentsRes.getDescription(),
                contentsRes.getType(),
                contentsRes.getIssueTitle(),
                contentsRes.getKeyword(),
                contentsRes.getPubDate()
        );
    }
}
