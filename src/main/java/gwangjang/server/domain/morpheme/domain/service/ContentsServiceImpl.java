package gwangjang.server.domain.morpheme.domain.service;

import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import gwangjang.server.domain.morpheme.domain.repository.ContentsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService{

    private final ContentsRepository contentsRepository;

    private final WebClient webClient;

    @Autowired
    public ContentsServiceImpl(ContentsRepository contentsRepository) {
        this.contentsRepository = contentsRepository;
        this.webClient = WebClient.create("https://www.googleapis.com/youtube/v3");
    }
    @Override
    public Mono<Void> saveYoutubeContent(String search) {
        return searchYoutube(search)
                .flatMap(response -> {
                    // YouTube API 응답을 파싱하고 비디오 정보를 추출합니다.
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray items = jsonResponse.getJSONArray("items");

                    // 각 항목을 반복하면서 Contents 엔티티에 저장합니다.
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        Contents youtubeContent = createYoutubeContent(item);
                        saveContent(youtubeContent);
                    }

                    return Mono.empty();
                });
    }

    private Mono<String> searchYoutube(String search) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("key", "AIzaSyB0GR5Aeo7FwtCm8h1L9CaEkvF_KjHzOOY")
                        .queryParam("part", "snippet")
                        .queryParam("type", "video")
                        .queryParam("maxResults", 20)
                        .queryParam("videoEmbeddable", true)
                        .queryParam("q", search)
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

        // Contents 엔티티를 생성하여 필드 값을 설정합니다.
        Contents youtubeContent = new Contents();
        youtubeContent.setType(ApiType.YOUTUBE);
        youtubeContent.setUrl(videoId);
        youtubeContent.setTitle(title);
        youtubeContent.setDescription(description);

        return youtubeContent;
    }

    private void saveContent(Contents content) {
        // Contents 엔티티를 저장하는 로직을 추가합니다.
        contentsRepository.save(content);
    }
}
