package com.team.togethart.repository.like;

import com.team.togethart.dto.like.LikeRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {

    int selectLike(LikeRequest likeRequest);

    void insertLike(LikeRequest likeRequest);

    void deleteLike(LikeRequest likeRequest);

    int selectCount(Long artworkId);

    void updateLikeCountPlus(Long artworkId);

    void updateLikeCountMinus(Long artworkId);

}
