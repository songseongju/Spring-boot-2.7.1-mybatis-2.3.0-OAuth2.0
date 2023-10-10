package com.team.togethart.controller.subscription;

import com.team.togethart.dto.follow.FollowRequest;
import com.team.togethart.dto.subscription.AuthRequest;
import com.team.togethart.dto.subscription.SubscriptionDTO;
import com.team.togethart.dto.subscription.SubscriptionImage;
import com.team.togethart.dto.subscription.SubscriptionInfo;
import com.team.togethart.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/sub")
public class SubscriptionApiController {

    @Autowired
    private SubscriptionService subscriptionService;


    //구독 하기 // 작동
    @PostMapping("/makesub")
    public ResponseEntity<String> subscriptionUser(@RequestBody SubscriptionDTO subscriptionDTO) {

        boolean isSubscriptions = subscriptionService.isSubscriptionExists(subscriptionDTO);

        if (isSubscriptions) {
            return ResponseEntity.badRequest().body("이미 구독 중입니다 !");
        } else {
            subscriptionService.subscriptionUser(subscriptionDTO);
            return ResponseEntity.ok("구독을 시작합니다");
        }

    }
    
    // 구독 수동 취소 // 작동
    @PostMapping("/deletesub")
    public ResponseEntity<String> deletesub(@RequestBody SubscriptionDTO subscriptionDTO) {
        boolean isSubscriptions = subscriptionService.isSubscriptionExists(subscriptionDTO);

        if(isSubscriptions){
            subscriptionService.deletesub(subscriptionDTO);
            return ResponseEntity.ok("구독이 취소됐습니다 ..");
        } else {
            return ResponseEntity.badRequest().body("구독 정보가 존재하지 않습니다 ?");
        }

    }

    // 구독 상태인 지 확인
    @GetMapping("/sub-check")
    public ResponseEntity<Map<String, Boolean>> subcheck(@RequestParam("subscribeFrom") Long subscribeFrom, @RequestParam("subscribeTo") Long subscribeTo) {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setSubscribeFrom(subscribeFrom);
        subscriptionDTO.setSubscribeTo(subscribeTo);

        // followFrom과 followTo를 사용하여 팔로우 관계 확인
        boolean isSubscriptionExists = subscriptionService.isSubscriptionExists(subscriptionDTO);


        Map<String, Boolean> response = new HashMap<>();
        response.put("isSubscriptionExists", isSubscriptionExists);

        return ResponseEntity.ok(response);
    }


    //구독자 받을 수 있는 권한 얻기 // 작동
    @PostMapping("/getSubAuth")
    public ResponseEntity<String> updateMemberAuth(@RequestBody AuthRequest authRequest) {
        boolean isMemberAuthExists = subscriptionService.isMemberAuthExists(authRequest);
        boolean isCertified = subscriptionService.isCertified(authRequest);



        if(isMemberAuthExists){
            return ResponseEntity.badRequest().body("이미 구독 권한을 습득하셨습니다 !");
        }else if(isCertified){
            subscriptionService.updateMemberAuth(authRequest);
            return ResponseEntity.ok("회원 권한을 업데이트했습니다.");
        }else{
            return ResponseEntity.badRequest().body("자격이 부족합니다 ?");
        }

    }

    // 구독권한보내주기
    @GetMapping("/AuthStatus/{member_id}")
    public ResponseEntity<Integer> getAuthStatus(@PathVariable Long member_id) {
        int memberAuth = subscriptionService.getAuthStatus(member_id);
        return ResponseEntity.ok(memberAuth);
    }


    // 내가 구독하는 사람들 리스트 // 작동
    @GetMapping("/Isub/{member_id}")
    public ResponseEntity<List<SubscriptionInfo>> getsubscribeto(@PathVariable Long member_id) {
        List<SubscriptionInfo> subscribeto = subscriptionService.getsubscribeto(member_id);
        return ResponseEntity.ok(subscribeto);
    }

    // 나를 구독하는 사람들 리스트 // 작동
    @GetMapping("/Isubbed/{member_id}")
    public ResponseEntity<List<SubscriptionInfo>> getsubscribefrom(@PathVariable Long member_id) {
        List<SubscriptionInfo> subscribefrom = subscriptionService.getsubscribefrom(member_id);
        return ResponseEntity.ok(subscribefrom);
    }

    // 구독자 전용 작품 모아보기

    @GetMapping("/SubImage/{member_id}")
    public ResponseEntity<List<SubscriptionImage>> getsubimage(@PathVariable Long member_id) {
        List<SubscriptionImage> subimage = subscriptionService.getsubimage(member_id);
        return ResponseEntity.ok(subimage);
    }


}
