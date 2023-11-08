package gwangjang.server.domain.morpheme.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import gwangjang.server.domain.morpheme.domain.service.MorphemeService;
import gwangjang.server.domain.morpheme.domain.service.NewsAPIService;
import io.swagger.annotations.ApiOperation;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/morpheme")
@RequiredArgsConstructor
public class MorphemeController {

    private final NewsAPIService newsAPIService;
    private final MorphemeService morphemeService;
    @GetMapping("/analysis/{msg}")
    @ApiOperation("제발 돼.. 좀...")
    public String analysis(@PathVariable String msg) throws JsonProcessingException {
      String newsList = newsAPIService.naverAPI(msg);
      List<Token> newsAnalysis =newsAPIService.analysis(newsList);
      morphemeService.saveOrUpdateWord(newsAnalysis);
      return "success";
    }
}
