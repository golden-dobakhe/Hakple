package com.golden_dobakhe.HakPle.domain.post.post.service;

import com.golden_dobakhe.HakPle.domain.post.like.entity.BoardLike;
import com.golden_dobakhe.HakPle.domain.post.like.repository.BoardLikeRepository;
import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardRequestDto;
import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardResponseDto;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.post.post.repository.BoardRepository;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.global.entity.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    //private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final TagMappingService tagMappingService;
   // private final UserRepository userRepository;


    public Page<BoardResponseDto> getBoardsByAcademyCode(String academyCode, Pageable pageable) {

        Page<Board> boards = boardRepository.findByAcademyCodeAndStatus(
                academyCode, Status.ACTIVE, pageable
        );
        return boards.map(BoardResponseDto::convertToDto);
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        //User user = userRepository.findById(requestDto.getUserId())
        //       .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User user = new User();
        user.setId(requestDto.getUserId());

        Board board = new Board();
        //board.setUser(user);

        board.setUser(user);

        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setAcademyCode(requestDto.getAcademyCode());
        board.setStatus(requestDto.getStatus() != null ? requestDto.getStatus() : Status.ACTIVE);

        Board savedBoard = boardRepository.save(board);

        tagMappingService.linkHashtagsToBoard(savedBoard, requestDto.getHashtags());

        return BoardResponseDto.convertToDto(savedBoard);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setStatus(requestDto.getStatus());

        Board updatedBoard = boardRepository.save(board);
        return BoardResponseDto.convertToDto(updatedBoard);
    }

    @Transactional
    public void deactivateBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        board.setStatus(Status.INACTIVE);
        boardRepository.save(board);
    }

    @Transactional
    public void increaseViewCount(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        board.setViewCount(board.getViewCount() + 1);
    }




}

