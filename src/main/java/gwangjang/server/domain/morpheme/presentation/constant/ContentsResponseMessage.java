package gwangjang.server.domain.morpheme.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentsResponseMessage {

    GET_CONTENTS_SUCCESS("이슈 조회 완료");

    private final String message;

}
