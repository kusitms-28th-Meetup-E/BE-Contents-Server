package gwangjang.server.domain.morpheme.application.dto.req;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalReq {
    private Long issueId;
    private String issueTitle;
    private String imgUrl;
    private Long keywordId;
    private String keyword;
}
