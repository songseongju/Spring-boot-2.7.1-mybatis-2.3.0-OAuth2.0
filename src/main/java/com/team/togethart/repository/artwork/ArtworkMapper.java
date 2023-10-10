package com.team.togethart.repository.artwork;

import com.team.togethart.dto.artwork.ArtworkViewResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArtworkMapper {

    ArtworkViewResponse selectArtwork(Long artworkId);

    void updateViewCount(Long artworkId);

}
