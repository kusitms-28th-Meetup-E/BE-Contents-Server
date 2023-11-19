package gwangjang.server.domain.morpheme.domain.entity;

import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import gwangjang.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Data
@Entity
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
    private String pubDate;
    public Contents(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }


}