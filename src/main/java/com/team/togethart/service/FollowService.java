package com.team.togethart.service;

import com.team.togethart.dto.follow.FollowAddRequest;
import com.team.togethart.repository.follow.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FollowService {
    @Autowired
    private FollowMapper followMapper;

    public void followUser(FollowAddRequest followRequest) {


        followMapper.insertFollow(followRequest);
    }

    public void unfollowUser(FollowAddRequest followRequest) {
        followMapper.deleteFollow(followRequest);
    }

    public FollowAddRequest getFollow(FollowAddRequest followAddRequest) {
        followMapper.getFollow(followAddRequest);
        return followAddRequest;
    }

    public boolean isFollowExists(FollowAddRequest followRequest) {
        long followCount = followMapper.getFollow(followRequest);
        return followCount > 0;
    }

    public List<FollowAddRequest> getfollowto(Long member_id) {
        return followMapper.getfollowto(member_id);
    }

    public List<FollowAddRequest> getfollowfrom(Long member_id) {
        return followMapper.getfollowfrom(member_id);
    }
}