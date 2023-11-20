package gwangjang.server.domain.like.application.dto.res;

import gwangjang.server.domain.morpheme.domain.entity.Contents;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
