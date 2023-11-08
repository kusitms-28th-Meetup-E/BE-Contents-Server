package gwangjang.server.domain.morpheme.domain.repository;

import gwangjang.server.domain.morpheme.domain.entity.Morpheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MorphemeRepository extends JpaRepository<Morpheme,Long> {
    Morpheme findByWord(String word);
}
