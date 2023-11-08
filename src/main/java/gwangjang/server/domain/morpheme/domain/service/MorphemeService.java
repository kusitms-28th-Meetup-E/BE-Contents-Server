package gwangjang.server.domain.morpheme.domain.service;

import gwangjang.server.domain.morpheme.domain.entity.Morpheme;
import gwangjang.server.domain.morpheme.domain.repository.MorphemeRepository;
import gwangjang.server.global.annotation.DomainService;
import jakarta.transaction.Transactional;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MorphemeService {
    private final MorphemeRepository morphemeRepository;

    @Transactional
    public void saveOrUpdateWord(List<Token> tokens) {
        for (Token token : tokens) {
            String word = token.getMorph();
            System.out.println("save              " + word);
            Morpheme existingWord = morphemeRepository.findByWord(word);
            if (existingWord != null) {
                // 단어가 이미 존재하면 count를 업데이트
                existingWord.setCount(existingWord.getCount() + 1);
                System.out.println(existingWord.getWord());
                morphemeRepository.save(existingWord);
            } else {
                // 단어가 존재하지 않으면 새로운 레코드를 생성
                Morpheme newWord = new Morpheme();
                newWord.setWord(word);
                System.out.println("else save         " +word);
                newWord.setCount(1);
                newWord.setIssueId(20);
                morphemeRepository.save(newWord);
            }
        }
    }
}
