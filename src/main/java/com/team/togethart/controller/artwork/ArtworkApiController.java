package com.team.togethart.controller.artwork;

import com.team.togethart.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtworkApiController {

//    @Autowired
//    private ArtworkViewResponse artworkViewResponse;

    @Autowired
    private ArtworkService artworkService;

    @GetMapping("api/artwork/{artworkId}")
    public ResponseEntity<Object> artworkDetail(
            @PathVariable("artworkId") Long artworkId) {


        return ResponseEntity.status(200).body(artworkService.findArtwork(artworkId));
    }

}
