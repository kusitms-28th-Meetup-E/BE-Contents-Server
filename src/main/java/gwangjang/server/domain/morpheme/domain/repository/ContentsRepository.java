package gwangjang.server.domain.morpheme.domain.repository;

import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Integer> {

    List<Contents> findByType(ApiType type);
    List<Contents> findByIssueTitleLike(String issue);
    List<Contents> findByIssueTitleLikeAndTypeOrderedByPubDate(String keyword, ApiType type);
}
