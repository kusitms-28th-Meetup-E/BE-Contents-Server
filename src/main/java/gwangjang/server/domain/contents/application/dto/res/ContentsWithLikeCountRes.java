package gwangjang.server.domain.contents.application.dto.res;

import gwangjang.server.domain.contents.domain.entity.Contents;
import gwangjang.server.domain.contents.domain.entity.constant.ApiType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ContentsWithLikeCountRes {
    private Integer contents_id;
    private String url;
    private String title;
    private String description;

    ApiType type;

    private String issueTitle;
    private String keyword;
    private String pubDate;

    private String topic;

    private String imgUrl;
    private Long likeCount;
    private boolean userLiked;
}
