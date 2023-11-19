package gwangjang.server.domain.morpheme.domain.service;

import gwangjang.server.domain.morpheme.domain.entity.Contents;
import gwangjang.server.domain.morpheme.domain.entity.constant.ApiType;
import gwangjang.server.domain.morpheme.domain.repository.ContentsRepository;
import gwangjang.server.domain.morpheme.domain.service.ContentsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final ContentsRepository contentsRepository;
    private final WebClient webClient;

    @Autowired
    public ContentsServiceImpl(ContentsRepository contentsRepository) {
        this.contentsRepository = contentsRepository;
        this.webClient = WebClient.builder()
                .baseUrl("https://www.googleapis.com/youtube/v3")
                .build();
    }

    @Override
    public Mono<Void> saveYoutubeContent(String[] search) {
        List<Mono<Void>> saveMonos = new ArrayList<>();

        for (String singleSearch : search) {
            Mono<Void> searchResultMono = searchYoutube(singleSearch);
            saveMonos.add(searchResultMono);
        }

        return Flux.concat(saveMonos).then();
    }

    private Mono<Void> searchYoutube(String singleSearch) {
        Mono<String> searchResultMono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("key", "AIzaSyAJ7vbt7Mu4MY5Ng_gtP-K8nDcsEuS2684")
                        .queryParam("part", "snippet")
                        .queryParam("type", "video")
                        .queryParam("maxResults", 20)
                        .queryParam("videoEmbeddable", true)
                        .queryParam("q", singleSearch)
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        return searchResultMono.flatMap(response -> {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray items = jsonResponse.optJSONArray("items");

            if (items == null) {
                System.err.println("No items found in the response for search term: " + singleSearch);
                return Mono.empty();
            }

            List<Mono<Void>> contentMonos = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                System.out.println("Processing item: " + item);
                Contents youtubeContent = createYoutubeContent(item, singleSearch);
                contentMonos.add(saveContent(youtubeContent));
            }

            return Flux.concat(contentMonos).then();
        });
    }

    private Contents createYoutubeContent(JSONObject item, String singleSearch) {
        JSONObject id = item.optJSONObject("id");
        if (id == null) {
            System.err.println("No 'id' found in the item: " + item);
            return null;
        }

        String videoId = id.optString("videoId");
        if (videoId.isEmpty()) {
            System.err.println("No 'videoId' found in the 'id' of the item: " + item);
            return null;
        }

        JSONObject snippet = item.optJSONObject("snippet");
        if (snippet == null) {
            System.err.println("No 'snippet' found in the item: " + item);
            return null;
        }

        String title = snippet.optString("title");
        String description = snippet.optString("description");
        String pubDate = snippet.optString("publishedAt");

        Contents youtubeContent = new Contents();
        youtubeContent.setType(ApiType.YOUTUBE);
        youtubeContent.setUrl(videoId);
        youtubeContent.setTitle(title);
        youtubeContent.setDescription(description);
        youtubeContent.setPubDate(pubDate);
        youtubeContent.setIssueTitle(singleSearch);

        return youtubeContent;
    }

    @Transactional
    private Mono<Void> saveContent(Contents content) {
        try {
            contentsRepository.save(content);
            System.out.println("Content saved: " + content);
            return Mono.empty();
        } catch (Exception e) {
            System.err.println("Error saving content: " + content);
            e.printStackTrace();
            return Mono.error(e);
        }
    }

    public List<Contents> getContents(ApiType type) {
        return contentsRepository.findByType(type);
    }
}
