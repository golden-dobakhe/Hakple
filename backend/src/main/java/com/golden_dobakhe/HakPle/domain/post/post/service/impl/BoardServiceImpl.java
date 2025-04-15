package com.golden_dobakhe.HakPle.domain.post.post.service.impl;


import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardRequest;
import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardResponse;
import com.golden_dobakhe.HakPle.domain.post.post.entity.*;
import com.golden_dobakhe.HakPle.domain.post.post.exception.BoardException;
import com.golden_dobakhe.HakPle.domain.post.post.repository.*;
import com.golden_dobakhe.HakPle.domain.post.post.service.BoardService;

import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.entity.Status;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final TagMappingRepository tagMappingRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardReportRepository boardReportRepository;

    @Override
    @Transactional
    public BoardResponse createBoard(BoardRequest request, Long userId) {
        validateBoardRequest(request);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> BoardException.notFound());

        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .academyCode(request.getAcademyCode())
                .user(user)
                .status(Status.ACTIVE)
                .modificationTime(null)
                .build();

        board = boardRepository.save(board);

        if (request.getTags() != null) {
            for (String tagName : request.getTags()) {
                if (!StringUtils.hasText(tagName)) continue;

                Hashtag hashtag = hashtagRepository.findByHashtagNameAndAcademyCode(tagName, request.getAcademyCode())
                        .orElseGet(() -> hashtagRepository.save(Hashtag.builder()
                                .hashtagName(tagName)
                                .academyCode(request.getAcademyCode())
                                .build()));

                TagMapping tagMapping = TagMapping.builder()
                        .board(board)
                        .hashtag(hashtag)
                        .build();

                board.getTags().add(tagMapping);


                tagMappingRepository.save(tagMapping);
            }
        }

        return createBoardResponse(board);
    }

    @Override
    @Transactional
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> BoardException.notFound());

        board.validateStatus();
        board.increaseViewCount();
        boardRepository.save(board);

        return createBoardResponse(board);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getBoards(String academyCode, Pageable pageable) {
        if (!StringUtils.hasText(academyCode)) {
            throw BoardException.invalidRequest();
        }
        return boardRepository.findByAcademyCodeAndStatus(academyCode, Status.ACTIVE, pageable)
                .map(this::createBoardResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> searchBoards(String academyCode, String keyword, Pageable pageable) {
        if (!StringUtils.hasText(academyCode) || !StringUtils.hasText(keyword)) {
            throw BoardException.invalidRequest();
        }
        return boardRepository.searchBoards(academyCode, keyword, pageable)
                .map(this::createBoardResponse);
    }

    @Override
    @Transactional
    public BoardResponse updateBoard(Long id, BoardRequest request, Long userId) {
        validateBoardRequest(request);

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> BoardException.notFound());

        board.validateUser(userId);
        board.validateStatus();
        board.update(request.getTitle(), request.getContent());

        tagMappingRepository.deleteByBoard(board);

        if (request.getTags() != null) {
            for (String tagName : request.getTags()) {
                if (!StringUtils.hasText(tagName)) continue;

                Hashtag hashtag = hashtagRepository.findByHashtagNameAndAcademyCode(tagName, request.getAcademyCode())
                        .orElseGet(() -> hashtagRepository.save(Hashtag.builder()
                                .hashtagName(tagName)
                                .academyCode(request.getAcademyCode())
                                .build()));

                TagMapping tagMapping = TagMapping.builder()
                        .board(board)
                        .hashtag(hashtag)
                        .build();

                tagMappingRepository.save(tagMapping);
            }
        }

        return createBoardResponse(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long id, Long userId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> BoardException.notFound());

        board.validateUser(userId);
        board.validateStatus();
        board.setStatus(Status.INACTIVE);
        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void toggleLike(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardException.notFound());

        board.validateStatus();

        boardLikeRepository.findByBoardIdAndUserId(boardId, userId)
                .ifPresentOrElse(
                        boardLikeRepository::delete,
                        () -> {
                            BoardLike boardLike = BoardLike.builder()
                                    .board(board)
                                    .user(userRepository.findById(userId)
                                            .orElseThrow(() -> BoardException.notFound()))
                                    .build();
                            boardLikeRepository.save(boardLike);
                        }
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getBoardsByTag(String academyCode, String tag, Pageable pageable) {
        if (!StringUtils.hasText(academyCode) || !StringUtils.hasText(tag)) {
            throw BoardException.invalidRequest();
        }
        return boardRepository.findByTagAndAcademyCode(academyCode, tag, pageable)
                .map(this::createBoardResponse);
    }

    @Override
    @Transactional
    public void createBoardReport(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardException.notFound());

        board.validateStatus();

        if (boardReportRepository.findByBoardIdAndUserId(boardId, userId).isPresent()) {
            throw BoardException.invalidRequest();
        }


        BoardReport boardReport = BoardReport.builder()
                .board(board)
                .user(userRepository.findById(userId)
                        .orElseThrow(() -> BoardException.notFound()))
                .build();

        boardReportRepository.save(boardReport);
    }

    private BoardResponse createBoardResponse(Board board) {
        System.out.println("1");
        List<String> tags = board.getTags().stream()
                .map(tagMapping -> tagMapping.getHashtag().getHashtagName())
                .collect(Collectors.toList());

        System.out.println("2");

        return BoardResponse.builder()
                .board(board)
                .tags(tags)
                .build();
    }

    private void validateBoardRequest(BoardRequest request) {
        if (!StringUtils.hasText(request.getTitle())) {
            throw BoardException.invalidRequest();
        }
        if (!StringUtils.hasText(request.getContent())) {
            throw BoardException.invalidRequest();
        }
        if (!StringUtils.hasText(request.getAcademyCode())) {
            throw BoardException.invalidRequest();
        }
    }
}
