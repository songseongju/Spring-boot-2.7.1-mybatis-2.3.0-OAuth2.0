package com.team.togethart.service;


import com.team.togethart.dto.subscription.AuthRequest;
import com.team.togethart.dto.subscription.SubscriptionDTO;
import com.team.togethart.dto.subscription.SubscriptionImage;
import com.team.togethart.dto.subscription.SubscriptionInfo;
import com.team.togethart.repository.subscription.SubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    //구독하기
    public void subscriptionUser(SubscriptionDTO subscriptionDTO) {

        subscriptionMapper.insertSubscription(subscriptionDTO);
    }

    
    // 구독정보 존재하는지 검사하는 메서드에 씀
    public SubscriptionDTO getSubscription(SubscriptionDTO subscriptionDTO) {
        subscriptionMapper.getSubscription(subscriptionDTO);
        return subscriptionDTO;
    }

    // 구독정보 존재하는지
    public boolean isSubscriptionExists(SubscriptionDTO subscriptionDTO) {
        long subscriptionCount = subscriptionMapper.getSubscription(subscriptionDTO);
        return subscriptionCount > 0;
    }

    //구독 권한이 있는지 ?
    public boolean isMemberAuthExists(AuthRequest authRequest) {

        return subscriptionMapper.isMemberAuthExists(authRequest);
    }

    public boolean isCertified(AuthRequest authRequest) {
        return subscriptionMapper.isCertified(authRequest);
    }



    // 구독자 받을 권한 업데이트 해주기
    public void updateMemberAuth(AuthRequest authRequest) {

        subscriptionMapper.updateMemberAuth(authRequest);

    }

    // 구독자 권한 상태 보내주기

    public int getAuthStatus(Long member_id){
        return subscriptionMapper.getAuthStatus(member_id);
    }


    // 나를 구독하는 사람들 리스트
    public List<SubscriptionInfo> getsubscribeto(Long member_id) {
        return subscriptionMapper.getsubscribeto(member_id);
    }

    // 내가 구독하는 사람들 리스트
    public List<SubscriptionInfo> getsubscribefrom(Long member_id) {
        return subscriptionMapper.getsubscribefrom(member_id);
    }

    
    // 구독 수동 취소
    public void deletesub(SubscriptionDTO subscriptionDTO) {
        subscriptionMapper.deletesub(subscriptionDTO);
    }

    // 구독한 사람들의 구독자 전용 작품 모아보기

    public List<SubscriptionImage> getsubimage(Long member_id) {
        return subscriptionMapper.getsubimage(member_id);
    }
}


