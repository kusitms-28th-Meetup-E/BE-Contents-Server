package gwangjang.server.domain.contents.domain.service;

import gwangjang.server.domain.contents.application.dto.res.ContentsRes;
import gwangjang.server.domain.contents.application.mapper.ContentsMapper;
import gwangjang.server.domain.contents.domain.entity.Contents;
import gwangjang.server.domain.contents.domain.repository.ContentsRepository;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ImageUrlUpdateService {
    private final ContentsMapper contentsMapper;

    private final ContentsRepository contentsRepository;

    // ContentsMapper와 ContentsRepository를 주입
    public ImageUrlUpdateService(ContentsMapper contentsMapper, ContentsRepository contentsRepository) {
        this.contentsMapper = contentsMapper;
        this.contentsRepository = contentsRepository;
    }

    public void updateImageUrlAndSave(List<ContentsRes> contents) {
        contents.forEach(contentRes -> {
            Contents contentsEntity = contentsMapper.toEntity(contentRes);
            contentsRepository.save(contentsEntity);
        });
    }
    @Transactional
    public void updateImageUrl(List<ContentsRes> contentsResList) throws KeyManagementException, NoSuchAlgorithmException {

        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        //추가된 코드

        List<ContentsRes> updatedContentsResList = new ArrayList<>();

        for (ContentsRes contentsRes : contentsResList) {
            try {
                // URL에서 HTML을 가져옴
                Document document = Jsoup.connect(contentsRes.getUrl()).get();

                // 메타태그 선택
                Elements metaTags = document.select("meta");

                // 정규표현식을 미리 컴파일
                Pattern twitterPattern = Pattern.compile("name=\"twitter:image\"\\s*content=\"([^\"]+)\"");
                Pattern ogPattern = Pattern.compile("property=\"og:image\"\\s*content=\"([^\"]+)\"");

                // 각 메타태그의 name과 content 속성 출력
                for (Element metaTag : metaTags) {
                    String name = metaTag.attr("name");
                    String property = metaTag.attr("property");
                    String content = metaTag.attr("content");

                    // 정규표현식을 사용하여 twitter:image 또는 og:image의 content 속성 추출
                    Matcher twitterMatcher = twitterPattern.matcher(metaTag.outerHtml());
                    Matcher ogMatcher = ogPattern.matcher(metaTag.outerHtml());

                    if (twitterMatcher.find()) {
                        String twitterImageContent = twitterMatcher.group(1);
                        System.out.println(twitterImageContent);
                        contentsRes.setImgUrl(twitterImageContent);
                        contentsRepository.updateContentsImageUrl(contentsRes.getContents_id(), twitterImageContent);
                        break; // 이미지 URL을 찾았으면 더 이상 검색하지 않도록 종료
                    } else if (ogMatcher.find()) {
                        String ogImageContent = ogMatcher.group(1);
                        System.out.println(ogImageContent);
                        contentsRes.setImgUrl(ogImageContent);
                        contentsRepository.updateContentsImageUrl(contentsRes.getContents_id(), ogImageContent);
                        break; // 이미지 URL을 찾았으면 더 이상 검색하지 않도록 종료
                    }
                }
            } catch (IOException e) {
                // 예외 발생 시 로깅
                e.printStackTrace();
            }
        }
    }


    public Contents updateEntity(ContentsRes contentsRes) {
        return contentsMapper.toEntity(contentsRes);
    }
}
