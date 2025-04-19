package com.golden_dobakhe.HakPle.domain.user.admin.controller;

import com.golden_dobakhe.HakPle.domain.user.admin.dto.BoardReportDto;
import com.golden_dobakhe.HakPle.domain.user.admin.dto.CommentReportDto;
import com.golden_dobakhe.HakPle.domain.user.admin.service.BoardReportService;
import com.golden_dobakhe.HakPle.domain.user.admin.service.CommentReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/report")
@RequiredArgsConstructor
@Tag(name = "🚨 신고 관리", description = "신고된 댓글 및 게시글 관련 API")
public class ApiV1ReportAdminController {

    @Qualifier("adminCommentReportService")
    private final CommentReportService commentReportService;
    private final BoardReportService boardReportService;

    @GetMapping("/comments")
    @Operation(
            summary = "신고된 댓글 목록 조회",
            description = "페이징 정보를 기반으로 신고된 댓글 리스트를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    public Page<CommentReportDto> getReportedComments(
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(name ="page",defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(name = "size",defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationTime"));
        return commentReportService.getReportedComments(pageable);
    }

    @GetMapping("/boards")
    @Operation(
            summary = "신고된 게시글 목록 조회",
            description = "신고된 게시글과 신고당한 유저의 정보를 페이징하여 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    public Page<BoardReportDto> getReportedBoards(
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(name ="page",defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(name = "size",defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationTime"));
        return boardReportService.getReportedBoards(pageable);
    }

}

