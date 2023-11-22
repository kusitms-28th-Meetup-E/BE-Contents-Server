package gwangjang.server.domain.like.domain.repository;

import gwangjang.server.domain.like.domain.entity.ContentLike;

import gwangjang.server.domain.contents.domain.entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<ContentLike, Long> {
    Optional<ContentLike> findByLoginIdAndContents(String loginId, Contents contents);
}