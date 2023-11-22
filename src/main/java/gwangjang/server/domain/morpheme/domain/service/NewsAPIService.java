package gwangjang.server.domain.morpheme.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import gwangjang.server.domain.morpheme.application.dto.res.ContentsRes;
import gwangjang.server.domain.morpheme.application.mapper.ContentsMapper;
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

    private final ContentsMapper contentsMapper ;

    public NewsAPIService(RestTemplate restTemplate, ContentsRepository contentsRepository, ContentsMapper contentsMapper) {
        this.restTemplate = restTemplate;
        this.contentsRepository = contentsRepository;
        this.contentsMapper = contentsMapper;
    }

    public String naverAPI(String name) {
        StringBuilder result = new StringBuilder();

        try {
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

            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(json);
            JSONArray items = (JSONArray) jsonData.get("items");

            for (Object obj : items) {
                JSONObject item = (JSONObject) obj;

                String title = (String) item.get("title");
                String description = (String) item.get("description");
                String url = (String) item.get("originallink");
                String pubDate = (String) item.get("pubDate");
                ContentsRes contentsRes = new ContentsRes();
                contentsRes.setTitle(title);
                contentsRes.setDescription(description);
                contentsRes.setUrl(url);
                contentsRes.setPubDate(pubDate);
                contentsRes.setType(ApiType.NAVER);
                contentsRes.setIssueTitle(name);
                contentsRepository.save(contentsMapper.toEntity(contentsRes));
            }
        } catch (org.json.simple.parser.ParseException e) {
            // Handle the ParseException locally
            e.printStackTrace(); // Add your handling logic here
            // You might want to log the error or return an error message
            result.append("Error parsing JSON response");
        } catch (Exception e) {
            // Handle other exceptions if needed
            e.printStackTrace();
        }

        return result.toString();
    }
}
