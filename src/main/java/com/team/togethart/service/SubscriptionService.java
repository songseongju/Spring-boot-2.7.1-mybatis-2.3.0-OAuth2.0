package com.team.togethart.service;


import com.team.togethart.dto.subscription.DeleteSubDTO;
import com.team.togethart.dto.subscription.InfoResponseDTO;
import com.team.togethart.dto.subscription.SubscriptionAddRequest;
import com.team.togethart.dto.subscription.SubscriptionAuthRequest;
import com.team.togethart.repository.subscription.SubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    public void subscriptionUser(SubscriptionAddRequest subscriptionAddRequest) {

        subscriptionMapper.insertSubscription(subscriptionAddRequest);
    }

    public SubscriptionAddRequest getSubscription(SubscriptionAddRequest subscriptionAddRequest) {
        subscriptionMapper.getSubscription(subscriptionAddRequest);
        return subscriptionAddRequest;
    }

    public boolean isSubscriptionExists(SubscriptionAddRequest subscriptionAddRequest) {
        long subscriptionCount = subscriptionMapper.getSubscription(subscriptionAddRequest);
        return subscriptionCount > 0;
    }

    public void updateMemberAuth(Long member_id) {
        SubscriptionAuthRequest subscriptionAuthRequest = new SubscriptionAuthRequest();
        subscriptionAuthRequest.setMember_id(member_id);

        subscriptionMapper.updateMemberAuth(subscriptionAuthRequest);

    }

    public List<InfoResponseDTO> getsubscribeto(Long member_id) {
        return subscriptionMapper.getsubscribeto(member_id);
    }

    public List<InfoResponseDTO> getsubscribefrom(Long member_id) {
        return subscriptionMapper.getsubscribefrom(member_id);
    }

    public void deletesub(DeleteSubDTO deleteSubDTO) {
        subscriptionMapper.deletesub(deleteSubDTO);
    }
}


