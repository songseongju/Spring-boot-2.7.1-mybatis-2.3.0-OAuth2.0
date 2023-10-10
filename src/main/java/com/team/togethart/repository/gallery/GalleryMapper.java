package com.team.togethart.repository.gallery;

import com.team.togethart.dto.gallery.GalleryViewResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface GalleryMapper {
    List<GalleryViewResponse> getGalleryImage();
}
