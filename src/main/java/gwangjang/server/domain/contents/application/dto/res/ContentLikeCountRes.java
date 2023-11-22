package gwangjang.server.domain.contents.application.dto.res;

import gwangjang.server.domain.contents.domain.entity.constant.ApiType;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentLikeCountRes {
    private Integer contents_id;
    private String url;
    private String title;
    private String description;
    ApiType type;
    private String issueTitle;
    private String keyword;
    private String pubDate;
    private String topic;
}
