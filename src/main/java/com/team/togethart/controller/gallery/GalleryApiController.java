package com.team.togethart.controller.gallery;


import com.team.togethart.dto.gallery.GalleryViewResponse;
import com.team.togethart.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/main")
public class GalleryApiController {

    @Autowired
    private GalleryService galleryService;

    @GetMapping("/index")
    public ResponseEntity<List<GalleryViewResponse>> mainIndex() {
        List<GalleryViewResponse> galleryImage = galleryService.getGalleryImage();
        return ResponseEntity.ok(galleryImage);
    }



}