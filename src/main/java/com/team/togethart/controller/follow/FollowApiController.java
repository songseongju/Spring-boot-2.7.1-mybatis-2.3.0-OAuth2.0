package com.team.togethart.controller.follow;

import com.team.togethart.dto.follow.FollowAddRequest;
import com.team.togethart.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/follow")
public class FollowApiController {
    @Autowired
    private FollowService followService;

    @PostMapping("/makefollow")
    public ResponseEntity<String> followUser(@RequestBody FollowAddRequest followRequest) {

        boolean isFollowExists = followService.isFollowExists(followRequest);

        if (isFollowExists) {
            return ResponseEntity.badRequest().body("이미 팔로우 관계가 존재합니다.");
        } else {
            followService.followUser(followRequest);
            return ResponseEntity.ok("팔로우 성공 !");
        }
    }


    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody FollowAddRequest followRequest) {

        boolean isFollowExists = followService.isFollowExists(followRequest);

        if (isFollowExists) {
            followService.unfollowUser(followRequest);
            return ResponseEntity.ok("언팔로우 성공 ...");
        } else {
            return ResponseEntity.badRequest().body("팔로우 관계가 존재하지 않습니다.");
        }

    }


    // 내가 팔로우 하는 사람들 리스트
    @GetMapping("/followfrom/{member_id}")
    public ResponseEntity<List<FollowAddRequest>> getfollowto(@PathVariable Long member_id) {
        List<FollowAddRequest> followto = followService.getfollowto(member_id);
        return ResponseEntity.ok(followto);
    }

    // 나를 팔로우 하는 사람들 리스트
    @GetMapping("/followto/{member_id}")
    public ResponseEntity<List<FollowAddRequest>> getfollowfrom(@PathVariable Long member_id) {
        List<FollowAddRequest> followfrom = followService.getfollowfrom(member_id);
        return ResponseEntity.ok(followfrom);
    }
}
