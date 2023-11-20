package gwangjang.server.domain.like.presentation;

import gwangjang.server.domain.like.application.dto.res.ContentLikeRes;
import gwangjang.server.domain.like.domain.service.LikeService;
import gwangjang.server.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static gwangjang.server.domain.like.presentation.constant.LikeResponse.LIKE_SUCCESS;
import static gwangjang.server.domain.like.presentation.constant.LikeResponse.ALREADY;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PutMapping("/{contentsId}")
    public ResponseEntity<SuccessResponse<ContentLikeRes>> addLike(@RequestHeader(value = "user-id") String socialId, @PathVariable Integer contentsId) {

        ContentLikeRes response;

        if (socialId != null && !socialId.isEmpty()) {
            response = likeService.addLike(socialId, contentsId);

            // 좋아요가 추가되었거나 이미 누른 경우에 대한 응답 메시지 설정
            String message = (response.getStatus() != "좋아요") ? ALREADY.getMessage() : LIKE_SUCCESS.getMessage();
            return ResponseEntity.ok(SuccessResponse.create(message, response));
        }

        // socialId가 없는 경우에 대한 응답 처리
        return ResponseEntity.badRequest().build();
    }




}
