package gwangjang.server.domain.morpheme.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import gwangjang.server.domain.morpheme.domain.repository.ContentsRepository;
import gwangjang.server.global.annotation.DomainService;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@DomainService
@Transactional
public class NewsAPIService {

    @Value("${naver.client-id}")
    private String NAVER_API_ID;

    @Value("${naver.secret}")
    private String NAVER_API_SECRET;

    private final RestTemplate restTemplate;
    private final ContentsRepository contentsRepository;

    public NewsAPIService(RestTemplate restTemplate, ContentsRepository contentsRepository) {
        this.restTemplate = restTemplate;
        this.contentsRepository = contentsRepository;
    }

    public String naverAPI(String name) throws JsonProcessingException {
        StringBuilder result = new StringBuilder();

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com/")
                .path("/v1/search/news.json")
                .queryParam("query", name)
                .queryParam("display", 20)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", NAVER_API_ID)
                .header("X-Naver-Client-Secret", NAVER_API_SECRET)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(request, String.class);
        String json = responseEntity.getBody();
        System.out.println(json);

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(json);
            JSONArray items = (JSONArray) jsonData.get("items");

            for (Object obj : items) {
                JSONObject item = (JSONObject) obj;

                String title = (String) item.get("title");
                String description = (String) item.get("description");
                String url = (String) item.get("originallink");
                String pubDate = (String) item.get("pubDate");
                Contents contents = new Contents();
                contents.setTitle(title);
                contents.setDescription(description);
                contents.setUrl(url);
                contents.setPubDate(pubDate);
                contents.setType(ApiType.NAVER);
                contents.setIssueTitle(name);
                contentsRepository.save(contents);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
