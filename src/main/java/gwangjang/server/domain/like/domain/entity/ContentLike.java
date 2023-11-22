package gwangjang.server.domain.like.domain.entity;


import gwangjang.server.domain.contents.domain.entity.Contents;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Data
@Entity
public class ContentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    @ManyToOne
    @JoinColumn(name = "contents_id")
    private Contents contents;
    private String loginId;
    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedAt = LocalDateTime.now();

    public ContentLike(Contents contents, String loginId) {
        this.contents = contents;
        this.loginId = loginId;
    }
}
