package gwangjang.server.domain.contents.application.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import gwangjang.server.domain.contents.domain.entity.Contents;
import gwangjang.server.domain.contents.domain.entity.constant.ApiType;

public class ContentsWithLikeCount {
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

    private long likeStatus;


}
