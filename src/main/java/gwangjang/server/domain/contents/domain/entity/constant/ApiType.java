package gwangjang.server.domain.contents.domain.entity.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ApiType {
    NAVER("NAVER"),YOUTUBE("YOUTUBE");
    private final String type;
}


