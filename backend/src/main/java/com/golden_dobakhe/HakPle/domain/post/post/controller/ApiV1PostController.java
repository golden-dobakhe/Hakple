package com.golden_dobakhe.HakPle.domain.post.post.controller;

import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardRequest;
import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardResponse;
import com.golden_dobakhe.HakPle.domain.post.post.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "Posts", description = "게시물 관리 API")
public class ApiV1PostController {

    private final BoardService boardService;

    @Operation(summary = "게시물 생성", description = "새로운 게시물을 생성합니다.")
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(
            //@RequestHeader("X-User-Id") Long userId,
            //@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody BoardRequest request
    ) {
        //Long userId = userDetails.getMemberId();

        // 하드코딩 테스트용
        Long userId = 7L;
        return ResponseEntity.ok(boardService.createBoard(request, userId));
    }

    @Operation(summary = "게시물 ID로 조회", description = "특정 ID의 게시물을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoard(id));
    }

    @Operation(summary = "모든 게시물 조회", description = "모든 게시물을 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<BoardResponse>> getBoards(
            @RequestParam String academyCode,
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(size = 10) Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.DESC, "creationTime");
        Pageable adjustedPageable = PageRequest.of(page - 1, pageable.getPageSize(), sort);

        return ResponseEntity.ok(boardService.getBoards(academyCode, adjustedPageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BoardResponse>> searchBoards(
            @RequestParam String academyCode,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(size = 10) Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.DESC, "creationTime");
        Pageable adjustedPageable = PageRequest.of(page - 1, pageable.getPageSize(), sort);


        return ResponseEntity.ok(boardService.searchBoards(academyCode, keyword, adjustedPageable));
    }

    @Operation(summary = "게시물 수정", description = "특정 ID의 게시물을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(
            //@RequestHeader("X-User-Id") Long userId,
            //@AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @RequestBody BoardRequest request

    ) {
        //Long userId = userDetails.getMemberId();

        // 하드코딩 테스트용
        Long userId = 7L;
        return ResponseEntity.ok(boardService.updateBoard(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(
           // @RequestHeader("X-User-Id") Long userId,
           // @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id

    ) {
       // Long userId = userDetails.getMemberId();

        // 하드코딩 테스트용
        Long userId = 7L;
        boardService.deleteBoard(id, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시물 좋아요 토글", description = "게시물을 좋아요합니다.")
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> toggleLike(
            // @RequestHeader("X-User-Id") Long userId,
            // @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {

        //Long userId = userDetails.getMemberId();

        // 하드코딩 테스트용
        Long userId = 7L;
        boardService.toggleLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시물 특적 태그로 이동합니다", description = "특정 ID의 게시물 태그로 이동합니다.")
    @GetMapping("/tag/{tag}")
    public ResponseEntity<Page<BoardResponse>> getBoardsByTag(
            @RequestParam String academyCode,
            @PathVariable String tag,
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(size = 10) Pageable pageable

    ) {

        Sort sort = Sort.by(Sort.Direction.DESC, "creationTime");
        Pageable adjustedPageable = PageRequest.of(page - 1, pageable.getPageSize(), sort);


        return ResponseEntity.ok(boardService.getBoardsByTag(academyCode, tag, adjustedPageable));
    }

    @Operation(summary = "게시물 신고", description = "특정 ID의 게시물을 신고합니다.")
    @PostMapping("/{id}/report")
    public ResponseEntity<Void> createBoardReport(
            // @RequestHeader("X-User-Id") Long userId,
            // @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {

        //Long userId = userDetails.getMemberId();

        // 하드코딩 테스트용
        Long userId = 7L;
        boardService.createBoardReport(id, userId);
        return ResponseEntity.ok().build();
    }
}