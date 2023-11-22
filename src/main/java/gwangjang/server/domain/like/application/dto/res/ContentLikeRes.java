package gwangjang.server.domain.like.application.dto.res;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentLikeRes {
    private String status;
    private Integer contentsId;
    private String loginId;


}
