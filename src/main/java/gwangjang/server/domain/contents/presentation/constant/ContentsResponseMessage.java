package gwangjang.server.domain.contents.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentsResponseMessage {

    GET_CONTENTS_SUCCESS("컨텐츠 조회 완료"),
    GET_BUBBLE_SUCCESS("버블 조회 완료");
    private final String message;

}
