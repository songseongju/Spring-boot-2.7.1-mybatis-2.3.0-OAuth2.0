package com.team.togethart.repository.comment;

import com.team.togethart.dto.comment.CommentAddRequest;
import com.team.togethart.dto.comment.CommentUpdateRequest;
import com.team.togethart.dto.comment.CommentViewResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertComment(CommentAddRequest commentAddRequest);

    List<CommentViewResponse> selectCommentList(@Param("artworkId") Long artworkId);

    int updateComment(CommentUpdateRequest commentUpdateRequest);

    int deleteComment(Long commentId);

}
