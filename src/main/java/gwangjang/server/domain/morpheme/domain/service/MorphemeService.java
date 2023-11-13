package gwangjang.server.domain.morpheme.domain.service;

import gwangjang.server.domain.morpheme.domain.entity.Morpheme;
import gwangjang.server.domain.morpheme.domain.repository.MorphemeRepository;
import gwangjang.server.global.annotation.DomainService;
import jakarta.transaction.Transactional;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MorphemeService {
    private final MorphemeRepository morphemeRepository;

    @Transactional
    public void saveOrUpdateWord(List<Token> tokens) {

        List<Morpheme> entitiesToSave = new ArrayList<>();

        for (Token token : tokens) {
            String word = token.getMorph();
            Morpheme existingWord = morphemeRepository.findByWordAndIssueId(word, 23);

            if (existingWord != null) {
                // Word already exists, fetch it again to ensure it's tracked by JPA
                existingWord = morphemeRepository.findById(existingWord.getId()).orElse(null);

                // Update count
                existingWord.setCount(existingWord.getCount() + 1);
                System.out.println( existingWord.getWord() +  existingWord.getCount());
                // Add to the list of entities to save
                entitiesToSave.add(existingWord);
            } else {
                // Word doesn't exist, create a new record
                Morpheme newWord = new Morpheme();
                newWord.setWord(word);
                newWord.setCount(1);
                newWord.setIssueId(23);
                System.out.println(newWord.getWord() + newWord.getCount());
                // Add to the list of entities to save
                entitiesToSave.add(newWord);
            }
        }
        // Batch save all entities
        morphemeRepository.saveAll(entitiesToSave);
    }
}
