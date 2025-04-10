package com.golden_dobakhe.HakPle.domain.post.post.controller;

import com.golden_dobakhe.HakPle.domain.post.like.dto.BoardLikeStatusDto;
import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardRequestDto;
import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardResponseDto;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Hashtag;
import com.golden_dobakhe.HakPle.domain.post.post.service.BoardService;
import com.golden_dobakhe.HakPle.domain.post.post.service.HashtagService;
import com.golden_dobakhe.HakPle.domain.post.post.service.TagMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class ApiV1PostController {

    private final BoardService boardService;
    private final HashtagService hashtagService;
    private final TagMappingService tagMappingService;

    @GetMapping("/academy/{academyCode}")
    public ResponseEntity<Page<BoardResponseDto>> getBoardsByAcademy(
            @PathVariable String academyCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "creationTime,desc") String sort
             ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("creationTime")));

        Page<BoardResponseDto> boards = boardService.getBoardsByAcademyCode(academyCode, pageable);
        return ResponseEntity.ok(boards);
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(
            @RequestBody @Valid BoardRequestDto requestDto
    ) {
        BoardResponseDto response = boardService.createBoard(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id,
                                                        @RequestBody @Valid BoardRequestDto requestDto) {
        BoardResponseDto response = boardService.updateBoard(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateBoard(@PathVariable Long id) {
        boardService.deactivateBoard(id); // 상태를 inactive로 변경
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/views")
    public ResponseEntity<Void> increaseViewCount(@PathVariable Long id) {
        boardService.increaseViewCount(id);
        return ResponseEntity.ok().build();
    }


    // 모든 해시태그 조회
    @GetMapping("/hashtag/list")
    public ResponseEntity<List<Hashtag>> getAllHashtags(@RequestParam String academyCode) {
        return ResponseEntity.ok(hashtagService.getHashtagsByAcademy(academyCode));
    }

    // 해시태그 검색
    @GetMapping("/hashtag/search")
    public ResponseEntity<List<Hashtag>> searchHashtags(@RequestParam String keyword,
                                                        @RequestParam String academyCode) {
        return ResponseEntity.ok(hashtagService.searchHashtags(keyword, academyCode));
    }


    // 게시글에 해시태그 연결
    @PostMapping("/hashtag/{boardId}")
    public ResponseEntity<Void> linkHashtags(
            @PathVariable Long boardId,
            @RequestBody List<String> hashtagNames
    ) {
        Board board = new Board();
        board.setId(boardId); // 실제로는 boardService에서 조회 필요
        tagMappingService.linkHashtagsToBoard(board, hashtagNames);
        return ResponseEntity.ok().build();
    }

    // 게시글의 해시태그 조회
    @GetMapping("/hashtag/{boardId}")
    public ResponseEntity<List<Hashtag>> getHashtagsByBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(tagMappingService.getHashtagsByBoardId(boardId));
    }






}
