package com.golden_dobakhe.HakPle.domain.user.admin.service;

import com.golden_dobakhe.HakPle.domain.post.post.repository.BoardReportRepository;
import com.golden_dobakhe.HakPle.domain.user.admin.dto.BoardReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardReportService {
    private final BoardReportRepository boardReportRepository;

    public Page<BoardReportDto> getReportedBoards(Pageable pageable) {
        return boardReportRepository.findAllWithUserAndBoard(pageable)
                .map(report -> {
                    Long boardId = report.getBoard().getId();
                    int boardReportCount = boardReportRepository.countByBoardId(boardId);
                    return BoardReportDto.fromEntity(report, boardReportCount);
                });
    }
}