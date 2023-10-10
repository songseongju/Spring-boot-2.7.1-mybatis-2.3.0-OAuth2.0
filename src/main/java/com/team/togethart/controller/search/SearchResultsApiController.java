package com.team.togethart.controller.search;


import com.team.togethart.dto.search.SearchResultsResponse;
import com.team.togethart.service.SearchResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchResultsApiController {

    @Autowired
    private SearchResultsService searchResultsService;

    @GetMapping("/{keyword}")
    public ResponseEntity<List<SearchResultsResponse>> searchResults(@PathVariable String keyword) {
        List<SearchResultsResponse> searchResults = searchResultsService.getSearchResults(keyword);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }



}
