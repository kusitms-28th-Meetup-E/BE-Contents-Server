package gwangjang.server.domain.like.domain.entity;


import gwangjang.server.domain.morpheme.domain.entity.Contents;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Data
@Entity
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    @ManyToOne
    @JoinColumn(name = "contents_id")
    private Contents contents;
    private String loginId;

}
