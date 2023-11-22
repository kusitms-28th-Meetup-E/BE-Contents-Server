package gwangjang.server.domain.like.domain.service;

import gwangjang.server.domain.like.application.dto.res.ContentLikeRes;
import gwangjang.server.domain.like.domain.entity.ContentLike;
import gwangjang.server.domain.like.domain.repository.LikeRepository;
import gwangjang.server.domain.contents.domain.entity.Contents;
import gwangjang.server.domain.contents.domain.repository.ContentsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final ContentsRepository contentsRepository;

    public ContentLikeRes addLike(String loginId, Integer contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();

        //중복 좋아요 방지
        if(isNotAlreadyLike(loginId, contents)) {
            likeRepository.save(new ContentLike(contents, loginId));
            ContentLikeRes contentLikeRes = new ContentLikeRes();
            contentLikeRes.setLoginId(loginId); // 예시: ContentLike에 있는 ID를 ContentLikeRes에 설정
            contentLikeRes.setContentsId(contents.getContents_id());
            contentLikeRes.setStatus("좋아요");
            return contentLikeRes;
        }else {
            // 이미 좋아요를 누른 경우, 좋아요 취소
            ContentLike contentLike = likeRepository.findByLoginIdAndContents(loginId,contents)
                    .orElseThrow(() -> new EntityNotFoundException("해당 좋아요를 찾을 수 없습니다."));
            likeRepository.delete(contentLike);
            ContentLikeRes contentLikeRes = new ContentLikeRes();
            contentLikeRes.setContentsId(contentLike.getContents().getContents_id());
            contentLikeRes.setLoginId(loginId);
            contentLikeRes.setStatus("취소");
            return contentLikeRes;
        }
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(String loginId, Contents contents) {
        return likeRepository.findByLoginIdAndContents(loginId,contents).isEmpty();
    }
}
