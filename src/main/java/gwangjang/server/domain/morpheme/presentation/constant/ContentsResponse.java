package gwangjang.server.domain.morpheme.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentsResponse {


    GET_MY_CONTENTS("구독 주제별 콘텐츠 조회를 완료 하였습니다. ");

    private final String message;
    ;}