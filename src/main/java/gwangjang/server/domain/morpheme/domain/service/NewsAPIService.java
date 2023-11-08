package gwangjang.server.domain.morpheme.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/")
public class NewsAPIService {
    @Value("${naver.client-id}")
    private String NAVER_API_ID;

    @Value("${naver.secret}")
    private String NAVER_API_SECRET;
    private final RestTemplate restTemplate;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public NewsAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/naver/{name}")
    public String naverAPI(@PathVariable String name) throws JsonProcessingException {
        name = "쿠팡 에어컨";
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com/")
                .path("/v1/search/news.json")
                .queryParam("query", name) //query=검색어&display=10&start=1&sort=random
                .queryParam("display", 100)
                .queryParam("start", 1)
                .queryParam("sort", "date")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", NAVER_API_ID)
                .header("X-Naver-Client-Secret", NAVER_API_SECRET)
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        String json = result.getBody();
        System.out.println(json);

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(json);
            JSONArray items = (JSONArray) jsonData.get("items");

            for (Object obj : items) {
                JSONObject item = (JSONObject) obj;

                String title = (String) item.get("title");
                String description = (String) item.get("description");

                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println();
                analysis(title+description);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result.getBody();
    }
    public String analysis(String msg) {

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        KomoranResult analyzeResultList = komoran.analyze(msg);

        System.out.println(analyzeResultList.getPlainText());

        List<Token> tokenList = analyzeResultList.getTokenList();

        for (Token token : tokenList) {
            System.out.format("%s\n", token.getMorph());
        }
        return tokenList.get(0).getMorph();
    }
}
