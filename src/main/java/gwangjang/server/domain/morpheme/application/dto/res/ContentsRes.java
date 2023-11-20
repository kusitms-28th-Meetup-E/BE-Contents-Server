package gwangjang.server.domain.morpheme.application.dto.res;

import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ContentsRes {
    private Integer contents_id;
    private String url;
    private String title;
    private String description;
    ApiType type;
    private String issueTitle;
    private String keyword;
    private String pubDate;
}
