package gwangjang.server.domain.like.presentation;

import gwangjang.server.domain.like.application.dto.res.ContentLikeRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

//    private final LikeService likeService;
//
//    @PostMapping
//    public ResponseEntity<ContentLikeRes> heart(@RequestHeader(value = "user-id") String userId, @RequestBody Integer contentId) {
//        likeService.heart(userId,contentId);
//        return new ResponseEntity<>(contentId, HttpStatus.CREATED);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<ContentLikeRes> unHeart(@RequestBody @Valid ContentLikeRes contentLikeRes) {
//        likeService.unHeart(contentLikeRes);
//        return new ResponseEntity<>(contentLikeRes, HttpStatus.OK);
//    }

}
