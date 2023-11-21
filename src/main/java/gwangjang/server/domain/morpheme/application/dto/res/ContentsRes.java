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
    private String topic;

    private String imgUrl;

    public ContentsRes toDto(Contents contents) {
        ContentsRes contentsDto = new ContentsRes();
        contentsDto.setType(contents.getType());
        contentsDto.setUrl(contents.getUrl());
        contentsDto.setTitle(contents.getTitle());
        contentsDto.setDescription(contents.getDescription());
        contentsDto.setPubDate(contents.getPubDate());
        contentsDto.setIssueTitle(contents.getIssueTitle());
        contentsDto.setTopic(contents.getTopic());
        return contentsDto;
    }

}
