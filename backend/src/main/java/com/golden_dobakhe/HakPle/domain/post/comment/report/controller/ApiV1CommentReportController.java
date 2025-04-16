package com.golden_dobakhe.HakPle.domain.post.comment.report.controller;

import com.golden_dobakhe.HakPle.domain.post.comment.report.service.CommentReportService;

import com.golden_dobakhe.HakPle.security.AnotherCustomUserDetails;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments/reports")
@Tag(name = "CommentReport Controller", description = "댓글 신고를 처리하는 컨트롤러")
@RequiredArgsConstructor
public class ApiV1CommentReportController {
    public final CommentReportService commentReportService;


    @PostMapping("/{commentId}")
    public ResponseEntity<?> reportComment(
            @PathVariable(name = "commentId") Long commentId,
            @AuthenticationPrincipal AnotherCustomUserDetails principal
    ) {
        commentReportService.reportComment(commentId, principal.getUser().getId());
        return ResponseEntity.ok("댓글이 신고되었습니다.");
    }
}
