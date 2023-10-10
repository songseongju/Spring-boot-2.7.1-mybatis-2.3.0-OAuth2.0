package com.team.togethart.service;


import com.team.togethart.dto.search.SearchResultsResponse;

import com.team.togethart.repository.search.SearchResultsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchResultsService {

    @Autowired
    private SearchResultsMapper searchResultsMapper;

    public List<SearchResultsResponse> getSearchResults(String keyword){
        return searchResultsMapper.getSearchResults(keyword);
    }

}
