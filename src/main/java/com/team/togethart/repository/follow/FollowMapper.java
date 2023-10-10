package com.team.togethart.repository.follow;

import com.team.togethart.dto.follow.FollowArtwork;
import com.team.togethart.dto.follow.FollowRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    void insertFollow(FollowRequest followRequest);

    void deleteFollow(FollowRequest followRequest);

    long getFollow(FollowRequest followRequest);

    List<FollowRequest> getfollowto(Long memberId);

    List<FollowRequest> getfollowfrom(Long memberId);

    List<FollowArtwork> getfollowart(Long memberId);

    int getfollowfromcount(Long memberId);

    int getfollowtocount(Long memberId);
}
