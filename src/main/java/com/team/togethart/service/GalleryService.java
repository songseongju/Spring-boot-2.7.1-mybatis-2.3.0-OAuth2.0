package com.team.togethart.service;

import com.team.togethart.dto.gallery.GalleryViewResponse;
import com.team.togethart.repository.gallery.GalleryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GalleryService {

    @Autowired
    private GalleryMapper galleryMapper;

    public List<GalleryViewResponse> getGalleryImage(){
        return galleryMapper.getGalleryImage();
    }
}
