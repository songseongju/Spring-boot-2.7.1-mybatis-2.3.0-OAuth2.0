package com.team.togethart.repository.myPage;

import com.team.togethart.dto.myPage.MyPageArtworksResponse;
import com.team.togethart.dto.myPage.MyPageLikesResponse;
import com.team.togethart.dto.myPage.MyPageMemberInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {

    MyPageMemberInfoResponse selectMyPageMemberInfo(Long memberId);

    List<MyPageArtworksResponse> selectMyPageArtworks(Long memberId);

    List<MyPageLikesResponse> selectMyPageLikes(Long memberId);

}
