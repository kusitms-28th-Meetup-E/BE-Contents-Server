package gwangjang.server.domain.contents.application.dto.res;

import gwangjang.server.domain.contents.domain.entity.constant.ApiType;
import io.swagger.models.auth.In;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
public class ContentsLikeCount {
    private Integer contentsId;
    private String url;
    private String title;
    private String description;
    private ApiType type;
    private String issueTitle;
    private String keyword;
    private String pubDate;
    private String topic;
    private String imgUrl;
    private long likeCount; // Change from int to long
    private boolean userLiked;

    public ContentsLikeCount(
            Integer contentsId,
            String url,
            String title,
            String description,
            ApiType type,
            String issueTitle,
            String keyword,
            String pubDate,
            String topic,
            String imgUrl,
            long likeCount, // Change from int to long
            boolean userLiked
    ) {
        this.contentsId = contentsId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.type = type;
        this.issueTitle = issueTitle;
        this.keyword = keyword;
        this.pubDate = pubDate;
        this.topic = topic;
        this.imgUrl = imgUrl;
        this.likeCount = likeCount;
        this.userLiked = userLiked;
    }

    // Add getters for all fields
}
