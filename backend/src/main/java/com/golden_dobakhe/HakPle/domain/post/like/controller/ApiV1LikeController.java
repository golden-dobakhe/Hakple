package com.golden_dobakhe.HakPle.domain.post.like.controller;

import com.golden_dobakhe.HakPle.domain.post.like.dto.BoardLikeStatusDto;
import com.golden_dobakhe.HakPle.domain.post.like.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class ApiV1LikeController {

    private final BoardLikeService boardLikeService;
   // private final UserService userService;

    @PostMapping("/board/{id}")
    public ResponseEntity<BoardLikeStatusDto> toggleLike(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        boolean isLiked = boardLikeService.toggleLike(id, userId);
        int totalLikes = boardLikeService.getTotalLikes(id);
        return ResponseEntity.ok(new BoardLikeStatusDto(isLiked, totalLikes));
    }

    @GetMapping("/count/{boardId}")
    public ResponseEntity<Integer> getTotalLikes(@PathVariable Long boardId) {
        int totalLikes = boardLikeService.getTotalLikes(boardId);
        return ResponseEntity.ok(totalLikes);
    }
/*
    @GetMapping("/{userId}/count")
    public ResponseEntity<Integer> getUserTotalLikes(@PathVariable Long userId) {
        int totalLikes = userService.getUserTotalLikes(userId);
        return ResponseEntity.ok(totalLikes);
    }
    */

}
