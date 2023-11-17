package gwangjang.server.domain.morpheme.domain.repository;

import gwangjang.server.domain.morpheme.domain.entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Integer> {

}
