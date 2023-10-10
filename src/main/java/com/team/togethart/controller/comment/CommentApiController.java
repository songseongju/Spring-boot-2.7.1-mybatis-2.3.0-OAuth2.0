package com.team.togethart.controller.comment;

import com.team.togethart.dto.comment.CommentAddRequest;
import com.team.togethart.dto.comment.CommentUpdateRequest;
import com.team.togethart.dto.comment.CommentViewResponse;
import com.team.togethart.service.CommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comment/{artworkId}")
    public ResponseEntity<List<CommentViewResponse>> commentListGet(
            @PathVariable("artworkId") Long artworkId) {

        return ResponseEntity.status(200)
                .body(commentService.getCommentList(artworkId));

    }

    @PostMapping("/comment/new")
    public ResponseEntity<Object> commentAdd(
            @RequestBody CommentAddRequest commentAddRequest) {

        log.info("commentAddRequest: " + commentAddRequest);
        int insertCount = commentService.addComment(commentAddRequest);
        log.info("comment INSERT count: " + insertCount);

        // 제대로 동작 했으면 OK, 처리가 안 됐으면 Bad Request.
        return insertCount == 1
                ? ResponseEntity.status(200)
                    .body("Comment been registered.")
                : ResponseEntity.status(400)
                    .body("Bad Request.");
    }

    @PatchMapping("/comment/{commentId}/modify")
    public ResponseEntity<Object> commentModify(
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest) {

        commentUpdateRequest.setCommentId(commentId);

        int updateCount = commentService.modifyComment(commentUpdateRequest);

        return updateCount == 1
                ? ResponseEntity.status(200)
                    .body("Comment been modified.")
                : ResponseEntity.status(400)
                    .body("Bad Request.");
    }

    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<Object> commentRemove(
            @PathVariable("commentId") Long commentId) {

        int deleteCount = commentService.removeComment(commentId);

        return deleteCount == 1
                ? ResponseEntity.status(200)
                    .body("Comment been deleted.")
                : ResponseEntity.status(400)
                    .body("Bad Request.");
    }


}
