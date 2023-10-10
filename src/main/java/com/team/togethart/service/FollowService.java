package com.team.togethart.service;

import com.team.togethart.dto.follow.FollowArtwork;
import com.team.togethart.dto.follow.FollowRequest;
import com.team.togethart.repository.follow.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FollowService {
    @Autowired
    private FollowMapper followMapper;

    // 팔로우
    public void followUser(FollowRequest followRequest) {


        followMapper.insertFollow(followRequest);
    }

    // 언팔로우
    public void unfollowUser(FollowRequest followRequest) {
        followMapper.deleteFollow(followRequest);
    }


    // 팔로우 상태 확인하기 1
    public FollowRequest getFollow(FollowRequest followRequest) {
        followMapper.getFollow(followRequest);
        return followRequest;
    }

    // 팔로우 상태 확인하기 2
    public boolean isFollowExists(FollowRequest followRequest) {
        long followCount = followMapper.getFollow(followRequest);
        return followCount > 0;
    }

    public List<FollowRequest> getfollowto(Long member_id) {
        return followMapper.getfollowto(member_id);
    }

    public List<FollowRequest> getfollowfrom(Long member_id) {
        return followMapper.getfollowfrom(member_id);
    }


    public List<FollowArtwork> getfollowart(Long member_id){
        return followMapper.getfollowart(member_id);
    }

    public int getfollowfromcount(Long member_id) {
        return followMapper.getfollowfromcount(member_id);
    }

    public int getfollowtocount(Long member_id) {
        return followMapper.getfollowtocount(member_id);
    }
}