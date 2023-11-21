package gwangjang.server.domain.morpheme.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gwangjang.server.domain.like.domain.entity.ContentLike;
import gwangjang.server.domain.morpheme.application.dto.res.ContentsRes;
import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import gwangjang.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Entity
@AllArgsConstructor
public class Contents extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contents_id;
    private String url;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    ApiType type;

    private String issueTitle;
    private String keyword;
    private String pubDate;

    private String topic;

    private String imgUrl;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    List<ContentLike> like = new ArrayList<>();
    public Contents(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }


    public Contents(String title, String description, String url, String pubDate, String issueTitle) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.issueTitle = issueTitle;
    }

    public Contents(String url, String title, String description, ApiType type, String issueTitle, String keyword, String pubDate) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.type = type;
        this.issueTitle = issueTitle;
        this.keyword = keyword;
        this.pubDate = pubDate;
    }
}
