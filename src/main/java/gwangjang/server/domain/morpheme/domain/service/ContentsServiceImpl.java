package gwangjang.server.domain.morpheme.domain.service;

import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import gwangjang.server.domain.morpheme.domain.repository.ContentsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;


@Service
@Transactional
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService{

    private final ContentsRepository contentsRepository;

    private final WebClient webClient;

    @Override
    public Mono<Void> saveYoutubeContent(String search) {
        return searchYoutube(search)
                .map(response -> {
                    // YouTube API 응답을 파싱하고 비디오 정보를 추출합니다.
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray items = jsonResponse.getJSONArray("items");

                    // 각 항목을 반복하면서 Contents 엔티티에 저장합니다.
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        System.out.println();
                        Contents youtubeContent = createYoutubeContent(item);

                        saveContent(youtubeContent);
                    }

                    return Mono.empty();
                })
                .then(); // Mono<Void>를 반환하기 위해 then() 사용
    }


    private Mono<String> searchYoutube(String search) {
        System.out.println("test               " + search);
        String encodedSearch = UriUtils.encode(search, StandardCharsets.UTF_8);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("key", "AIzaSyB0GR5Aeo7FwtCm8h1L9CaEkvF_KjHzOOY")
                        .queryParam("part", "snippet")
                        .queryParam("type", "video")
                        .queryParam("maxResults", 20)
                        .queryParam("videoEmbeddable", true)
                        .queryParam("q", encodedSearch)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    private Contents createYoutubeContent(JSONObject item) {
        // 비디오 ID, 제목 및 설명을 추출합니다.
        JSONObject id = item.getJSONObject("id");
        String videoId = id.getString("videoId");

        JSONObject snippet = item.getJSONObject("snippet");
        String title = snippet.getString("title");
        String description = snippet.getString("description");
        String pudDate = snippet.getString("publishedAt");
        System.out.println(videoId + title + description + pudDate);

        // Contents 엔티티를 생성하여 필드 값을 설정합니다.
        Contents youtubeContent = new Contents();
        youtubeContent.setType(ApiType.YOUTUBE);
        youtubeContent.setUrl(videoId);
        youtubeContent.setTitle(title);
        youtubeContent.setDescription(description);
        youtubeContent.setPubDate(pudDate);

        return youtubeContent;
    }

    private void saveContent(Contents content) {
        contentsRepository.save(content);
    }

}
