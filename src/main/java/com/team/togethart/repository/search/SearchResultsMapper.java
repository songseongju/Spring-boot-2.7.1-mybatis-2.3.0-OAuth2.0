package com.team.togethart.repository.search;


import com.team.togethart.dto.search.SearchResultsRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchResultsMapper {

    List<SearchResultsRequest> getSearchResults(String keyword);
}
