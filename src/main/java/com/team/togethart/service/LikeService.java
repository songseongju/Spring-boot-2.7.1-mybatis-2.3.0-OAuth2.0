package com.team.togethart.service;

import com.team.togethart.dto.like.LikeRequest;
import com.team.togethart.repository.like.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    @Autowired
    private final LikeMapper likeMapper;

    @Autowired
    private LikeRequest likeRequest;

    public int findLike(LikeRequest likeRequest) {
        // DB 셀렉트 결과 0 이면 참 ==> 좋아요 기능 작동 가능.
        // 셀렉트 결과 0 이 아니면 거짓 ==> 좋아요 기능 작동 불가. 좋아요 취소 작동 가능.
        return likeMapper.selectLike(likeRequest) == 0
                ? 0
                : 1;
    }

    // 컨트롤러에게,
    // addLike 가 정상작동 하면 1 을 반환
    // 올바르지 않은 요청일 시 0 을 반환
    public int addLike(LikeRequest likeRequest) {

        // 셀렉트 결과 0 이면 좋아요 작동.
        if (findLike(likeRequest) == 0) {
            likeMapper.insertLike(likeRequest);
            likeMapper.updateLikeCountPlus(likeRequest.getArtworkId());
            return 1;
        } else return 0;

        // 좋아요 성공하면 해당 회원의 대상 작품의 카테고리에 대한 선호도 2~3 증가 (수치 논의 필요)

    }

    public int removeLike(Long artworkId, Long memberId) {

        LikeRequest request = new LikeRequest(artworkId, memberId);

        // 셀렉트 결과 1 이면 좋아요 취소 작동.
        if (findLike(request) == 1) {
            likeRequest.setArtworkId(artworkId);
            likeRequest.setMemberId(memberId);

            likeMapper.deleteLike(likeRequest);
            likeMapper.updateLikeCountMinus(likeRequest.getArtworkId());

            return 1;
        } else return 0;

    }

    public int countLike(Long artworkId) {

//        Map<String, Integer> map = new HashMap<String, Integer>();
//        map.put("likeCount", likeMapper.selectCount(artworkId));

        return likeMapper.selectCount(artworkId);

    }

}
