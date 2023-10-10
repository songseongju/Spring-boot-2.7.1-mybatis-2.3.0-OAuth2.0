package com.team.togethart.service;

import com.team.togethart.dto.comment.CommentAddRequest;
import com.team.togethart.dto.comment.CommentUpdateRequest;
import com.team.togethart.dto.comment.CommentViewResponse;
import com.team.togethart.repository.comment.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentViewResponse commentViewResponse;
    @Autowired
    private CommentAddRequest commentAddRequest;
    @Autowired
    private CommentUpdateRequest commentUpdateRequest;

    public int addComment(CommentAddRequest commentAddRequest) {

        if (commentAddRequest.getCommentContent() != null) {
            commentMapper.insertComment(commentAddRequest);
            return 1;
        }

        return 0;

    }

    public List<CommentViewResponse> getCommentList(Long artworkId) {
        return commentMapper.selectCommentList(artworkId);
    }


    public int modifyComment(CommentUpdateRequest commentUpdateRequest) {

        return commentUpdateRequest.getCommentContent().equals("")
                ? 0
                : commentMapper.updateComment(commentUpdateRequest);
    }

    public int removeComment(Long commentId) {
        return commentMapper.deleteComment(commentId);
    }

}
