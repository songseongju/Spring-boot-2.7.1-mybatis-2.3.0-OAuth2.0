package com.team.togethart.controller.follow;

import com.team.togethart.dto.follow.FollowArtwork;
import com.team.togethart.dto.follow.FollowRequest;
import com.team.togethart.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/follow")
public class FollowApiController {
    @Autowired
    private FollowService followService;

    @PostMapping("/makefollow")
    public ResponseEntity<String> followUser(@RequestBody FollowRequest followRequest) {

        boolean isFollowExists = followService.isFollowExists(followRequest);

        if (isFollowExists) {
            return ResponseEntity.badRequest().body("이미 팔로우 관계가 존재합니다.");
        } else {
            followService.followUser(followRequest);
            return ResponseEntity.ok("팔로우 성공 !");
        }
    }


    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody FollowRequest followRequest) {

        boolean isFollowExists = followService.isFollowExists(followRequest);

        if (isFollowExists) {
            followService.unfollowUser(followRequest);
            return ResponseEntity.ok("언팔로우 성공 ...");
        } else {
            return ResponseEntity.badRequest().body("팔로우 관계가 존재하지 않습니다.");
        }

    }

    // 팔로우 상태인 지 확인
    @GetMapping("/follow-check")
    public ResponseEntity<Map<String, Boolean>> followcheck(@RequestParam("followFrom") Long followFrom, @RequestParam("followTo") Long followTo) {
        FollowRequest followRequest = new FollowRequest();
        followRequest.setFollowFrom(followFrom);
        followRequest.setFollowTo(followTo);

        // followFrom과 followTo를 사용하여 팔로우 관계 확인
        boolean isFollowExists = followService.isFollowExists(followRequest);


        Map<String, Boolean> response = new HashMap<>();
        response.put("isFollowExists", isFollowExists);

        return ResponseEntity.ok(response);
    }




    // 나를 팔로우 하는 사람들 리스트
    @GetMapping("/followto/{member_id}")
    public ResponseEntity<List<FollowRequest>> getfollowto(@PathVariable Long member_id) {
        List<FollowRequest> followto = followService.getfollowto(member_id);
        return ResponseEntity.ok(followto);
    }




    // 내가 팔로우 하는 사람들 리스트
    @GetMapping("/followfrom/{member_id}")
    public ResponseEntity<List<FollowRequest>> getfollowfrom(@PathVariable Long member_id) {
        List<FollowRequest> followfrom = followService.getfollowfrom(member_id);
        return ResponseEntity.ok(followfrom);
    }

    // 내가 팔로우하는 사람들의 그림 모음
    @GetMapping("/artwork/{member_id}")
    public ResponseEntity<List<FollowArtwork>> getfollowart(@PathVariable Long member_id){
        List<FollowArtwork> followart = followService.getfollowart(member_id);
        return ResponseEntity.ok(followart);
    }

    // 나를 팔로우 한 사람들의 수
    @GetMapping("/followToCount/{member_id}")
    public ResponseEntity<Integer> getfollowtocount(@PathVariable Long member_id) {
        int followersCount = followService.getfollowtocount(member_id);
        return ResponseEntity.ok(followersCount);
    }

    // 내가 팔로우 한 사람들의 수
    @GetMapping("/followFromCount/{member_id}")
    public ResponseEntity<Integer> getfollowfromcount(@PathVariable Long member_id) {
        int followCount = followService.getfollowfromcount(member_id);
        return ResponseEntity.ok(followCount);
    }
}
