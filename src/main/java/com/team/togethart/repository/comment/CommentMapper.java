package com.team.togethart.repository.comment;

import com.team.togethart.dto.comment.CommentAddRequest;
import com.team.togethart.dto.comment.CommentViewResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertComment(CommentAddRequest commentAddRequest);

    List<CommentViewResponse> selectCommentList(Long artworkId);
}
