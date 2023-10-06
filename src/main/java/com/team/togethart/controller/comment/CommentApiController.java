package com.team.togethart.controller.comment;

import com.team.togethart.dto.comment.CommentAddRequest;
import com.team.togethart.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/{artworkId}")
    public ResponseEntity<Object> commentAdd(
            @RequestBody CommentAddRequest commentAddRequest) {

        commentService.addComment(commentAddRequest);

        return ResponseEntity.status(200).body("comment been registered.");
    }



}
