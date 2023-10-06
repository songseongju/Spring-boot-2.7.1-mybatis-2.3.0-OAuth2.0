package com.team.togethart.repository.subscription;


import com.team.togethart.dto.subscription.DeleteSubDTO;
import com.team.togethart.dto.subscription.InfoResponseDTO;
import com.team.togethart.dto.subscription.SubscriptionAddRequest;
import com.team.togethart.dto.subscription.SubscriptionAuthRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubscriptionMapper {

    void insertSubscription(SubscriptionAddRequest subscriptionAddRequest);

    long getSubscription(SubscriptionAddRequest subscriptionAddRequest);

    void updateMemberAuth(SubscriptionAuthRequest subscriptionAuthRequest);

    List<InfoResponseDTO> getsubscribeto(Long memberId);

    List<InfoResponseDTO> getsubscribefrom(Long memberId);

    void deletesub(DeleteSubDTO deleteSubDTO);
}
