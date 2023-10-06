package com.team.togethart.controller.subscription;

import com.team.togethart.dto.subscription.DeleteSubDTO;
import com.team.togethart.dto.subscription.InfoResponseDTO;
import com.team.togethart.dto.subscription.SubscriptionAddRequest;
import com.team.togethart.dto.subscription.SubscriptionAuthRequest;
import com.team.togethart.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sub")
public class SubscriptionApiController {

    @Autowired
    private SubscriptionService subscriptionService;


    //구독 하기
    @PostMapping("/makesub")
    public ResponseEntity<String> subscriptionUser(@RequestBody SubscriptionAddRequest subscriptionAddRequest) {

        boolean isSubscriptions = subscriptionService.isSubscriptionExists(subscriptionAddRequest);

        if (isSubscriptions) {
            return ResponseEntity.badRequest().body("이미 구독 중입니다 !");
        } else {
            subscriptionService.subscriptionUser(subscriptionAddRequest);
            return ResponseEntity.ok("구독을 시작합니다");
        }

    }

    //구독자 받을 수 있는 권한 얻기
    @PostMapping("/getSubAuth")
    public ResponseEntity<String> updateMemberAuth(@RequestBody SubscriptionAuthRequest subscriptionAuthRequest) {
        Long memberId = subscriptionAuthRequest.getMember_id();

        subscriptionService.updateMemberAuth(memberId);

        return ResponseEntity.ok("회원 권한을 업데이트했습니다.");
    }


    // 나를 구독하는 사람들 리스트
    @GetMapping("/Isubbed/{member_id}")
    public ResponseEntity<List<InfoResponseDTO>> getsubscribeto(@PathVariable Long member_id) {
        List<InfoResponseDTO> subscribeto = subscriptionService.getsubscribeto(member_id);
        return ResponseEntity.ok(subscribeto);
    }

    // 내가 구독하는 사람들 리스트
    @GetMapping("/Isub/{member_id}")
    public ResponseEntity<List<InfoResponseDTO>> getsubscribefrom(@PathVariable Long member_id) {
        List<InfoResponseDTO> subscribefrom = subscriptionService.getsubscribefrom(member_id);
        return ResponseEntity.ok(subscribefrom);
    }

    @PostMapping("/deletesub")
    public ResponseEntity<String> deletesub(@RequestBody DeleteSubDTO deleteSubDTO) {
        subscriptionService.deletesub(deleteSubDTO);
        return ResponseEntity.ok().build();
    }
}
