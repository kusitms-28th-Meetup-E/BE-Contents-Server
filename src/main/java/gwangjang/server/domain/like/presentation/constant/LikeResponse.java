package gwangjang.server.domain.like.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeResponse {


    LIKE_SUCCESS("좋아요 성공"),
    ALREADY("좋아요 취소");

    private final String message;

}
