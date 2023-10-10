package com.team.togethart.service;

import com.team.togethart.dto.myPage.MyPageArtworksResponse;
import com.team.togethart.dto.myPage.MyPageLikesResponse;
import com.team.togethart.dto.myPage.MyPageMemberInfoResponse;
import com.team.togethart.repository.myPage.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {

    @Autowired
    MyPageMapper myPageMapper;

    public MyPageMemberInfoResponse getMyPageMemberInfo(Long memberId) {
        return myPageMapper.selectMyPageMemberInfo(memberId);
    }

    public List<MyPageArtworksResponse> getMyPageArtworks(Long memberId) {
        return myPageMapper.selectMyPageArtworks(memberId);
    }

    public List<MyPageLikesResponse> getMyPageLikes(Long memberId) {
        return myPageMapper.selectMyPageLikes(memberId);
    }

}
