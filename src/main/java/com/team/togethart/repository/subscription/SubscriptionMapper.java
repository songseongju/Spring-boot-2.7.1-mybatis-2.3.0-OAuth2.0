package com.team.togethart.repository.subscription;


import com.team.togethart.dto.subscription.AuthRequest;
import com.team.togethart.dto.subscription.SubscriptionDTO;
import com.team.togethart.dto.subscription.SubscriptionImage;
import com.team.togethart.dto.subscription.SubscriptionInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubscriptionMapper {

    void insertSubscription(SubscriptionDTO subscriptionDTO);

    long getSubscription(SubscriptionDTO subscriptionDTO);


    // 구독 권한 얻었는지 확인


    boolean isMemberAuthExists(AuthRequest authRequest);
    // 구독 권한 업데이트

    boolean isCertified(AuthRequest authRequest);

    void updateMemberAuth(AuthRequest authRequest);


    // 구독권한정보 넘겨주기
    int getAuthStatus(Long member_id);




    List<SubscriptionInfo> getsubscribeto(Long member_id);

    List<SubscriptionInfo> getsubscribefrom(Long member_id);

    void deletesub(SubscriptionDTO subscriptionDTO);

    // 구독전용작품 모아보기

    List<SubscriptionImage> getsubimage(Long member_id);
}
