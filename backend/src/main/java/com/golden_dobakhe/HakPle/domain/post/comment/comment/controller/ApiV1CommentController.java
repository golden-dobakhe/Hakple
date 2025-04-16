package com.golden_dobakhe.HakPle.domain.post.comment.comment.controller;

import com.golden_dobakhe.HakPle.domain.post.comment.CommentResult;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.dto.CommentRequestDto;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.dto.CommentResponseDto;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.entity.Comment;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Tag(name = "Comment Controller", description = "댓글 CRUD를 처리하는 컨트롤러")
public class ApiV1CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "새로운 댓글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 저장 완료"),
            @ApiResponse(responseCode = "400", description = "유저/게시글 없음 또는 댓글 내용 비어 있음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody CommentRequestDto commentRequestDto,
                                              @AuthenticationPrincipal AnotherCustomUserDetails principal) {
        Comment comment = commentService.commentSave(commentRequestDto,principal.getUser().getId());

        if (comment != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CommentResponseDto(comment.getId(), "댓글 저장 완료"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 저장 실패");
    }

    @Operation(summary = "댓글 수정", description = "기존 댓글을 수정합니다. boardId 요청 필요X")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 완료"),
            @ApiResponse(responseCode = "400", description = "유저 없음, 댓글 없음, 내용 비어 있음"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody CommentRequestDto commentRequestDto,
                                                @AuthenticationPrincipal AnotherCustomUserDetails principal) {
        CommentResult result = commentService.commentUpdate(commentRequestDto,principal.getUser().getId());

        switch (result) {
            case USER_NOT_FOUND:
                return ResponseEntity.badRequest().body("댓글 수정 실패 : 사용자를 찾을 수 없습니다");
            case COMMENT_NOT_FOUND:
                return ResponseEntity.badRequest().body("댓글 수정 실패 : 없는 댓글 입니다");
            case EMPTY:
                return ResponseEntity.badRequest().body("댓글 수정 실패 : Comment Empty 입니다");
            case UNAUTHORIZED:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 수정 실패 : 권한이 없습니다");
            case SUCCESS:
                return ResponseEntity.ok("댓글 수정 완료");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정 실패 : 서버 오류");
        }
    }

    @Operation(summary = "댓글 삭제", description = "댓글 ID로 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 완료"),
            @ApiResponse(responseCode = "400", description = "유저 또는 댓글 없음"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "commentId") Long commentId,
                                                @AuthenticationPrincipal AnotherCustomUserDetails principal) {
        CommentResult result = commentService.commentDelete(commentId,principal.getUser().getId());

        switch (result) {
            case USER_NOT_FOUND:
                return ResponseEntity.badRequest().body("댓글 삭제 실패 : 사용자를 찾을 수 없습니다");
            case COMMENT_NOT_FOUND:
                return ResponseEntity.badRequest().body("댓글 삭제 실패 : 없는 댓글 입니다");
            case UNAUTHORIZED:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 삭제 실패 : 권한이 없습니다");
            case SUCCESS:
                return ResponseEntity.ok("댓글 삭제 완료");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 실패 : 서버 오류");
        }
    }
}

