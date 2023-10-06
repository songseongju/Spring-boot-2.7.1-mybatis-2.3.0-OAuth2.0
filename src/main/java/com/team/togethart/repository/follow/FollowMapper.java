package com.team.togethart.repository.follow;

import com.team.togethart.dto.follow.FollowAddRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    void insertFollow(FollowAddRequest followRequest);

    void deleteFollow(FollowAddRequest followRequest);

    long getFollow(FollowAddRequest followAddRequest);

    List<FollowAddRequest> getfollowto(Long memberId);

    List<FollowAddRequest> getfollowfrom(Long memberId);
}
