package com.team.togethart.controller.like;

import com.team.togethart.dto.like.LikeRequest;
import com.team.togethart.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private LikeRequest likeRequest;

    @PostMapping("/like")
    public ResponseEntity<Object> likeAdd(
            @RequestBody LikeRequest likeRequest) {

        if (likeService.addLike(likeRequest) == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body("라이크 성공.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

    }

    @DeleteMapping("/unlike/{artworkId}/{memberId}")
    public ResponseEntity<?> likeRemove(
            @PathVariable("artworkId") Long artworkId,
            @PathVariable("memberId") Long memberId) {

        if (likeService.removeLike(artworkId, memberId) == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("라이크 취소 성공.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }

    @GetMapping("/hasLike/{artworkId}/{memberId}")
    public ResponseEntity<Object> hasLike(
            @PathVariable("artworkId") Long artworkId,
            @PathVariable("memberId") Long memberId) {

        likeRequest.setArtworkId(artworkId);
        likeRequest.setMemberId(memberId);

//        Map<String, Integer> map = new HashMap<String, Integer>();
//
//        if (likeService.findLike(likeRequest) == 0) {
//            map.put("hasLike", 0);
//        } else if (likeService.findLike(likeRequest) == 1) {
//            map.put("hasLike", 1);
//        }
        // 결과 0 이면 좋아요 기능 작동 가능
        // 결과 1 이면 좋아요 취고 시능 작동 가능을 알림.
        return likeService.findLike(likeRequest) == 0
            || likeService.findLike(likeRequest) == 1
                ? ResponseEntity.status(200).body(likeService.findLike(likeRequest))
                : ResponseEntity.status(400).body(likeService.findLike(likeRequest));

    }

    @GetMapping("/likeCount/{artworkId}")
    public ResponseEntity<Integer> likeCount(
            @PathVariable("artworkId") Long artworkId) {

        likeService.countLike(artworkId);

        return ResponseEntity.status(HttpStatus.OK).body(likeService.countLike(artworkId));

    }

}
