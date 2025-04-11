package com.golden_dobakhe.HakPle.domain.post.post.service;


import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardRequest;
import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    BoardResponse createBoard(BoardRequest request, Long userId);
    BoardResponse getBoard(Long id);
    Page<BoardResponse> getBoards(String academyCode, Pageable pageable);
    Page<BoardResponse> searchBoards(String academyCode, String keyword, Pageable pageable);
    BoardResponse updateBoard(Long id, BoardRequest request, Long userId);
    void deleteBoard(Long id, Long userId);
    void toggleLike(Long boardId, Long userId);
    Page<BoardResponse> getBoardsByTag(String academyCode, String tag, Pageable pageable);
    void createBoardReport(Long boardId, Long userId);
}