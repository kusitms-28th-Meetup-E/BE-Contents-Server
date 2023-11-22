package gwangjang.server.domain.contents.application.dto.res;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentsDataRes {
    private String Domain;
    private String issue;
    private String keyword;
    private String type;
    private String title;
    private String imgUrl;
    private String content;

    public ContentsDataRes(String domain, String issue, String keyword, String type, String title, String content) {
        Domain = domain;
        this.issue = issue;
        this.keyword = keyword;
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public void updateImgUrl(String url){
        this.imgUrl = url;
    }
}
