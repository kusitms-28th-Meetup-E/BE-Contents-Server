package gwangjang.server.domain.contents.application.dto.res;


import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
public class BubbleChartRes {
    String issueTitle;
    String keyword;
    String date;
    Long rank;

    public BubbleChartRes(String issueTitle, String keyword, String date, Long rank) {
        this.issueTitle = issueTitle;
        this.keyword = keyword;
        this.date = date;
        this.rank = rank;
    }
}
