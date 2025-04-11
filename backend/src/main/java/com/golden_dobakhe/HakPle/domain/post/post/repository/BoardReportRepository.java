package com.golden_dobakhe.HakPle.domain.post.post.repository;

import com.golden_dobakhe.HakPle.domain.post.post.entity.BoardReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardReportRepository extends JpaRepository<BoardReport, Long> {
    Optional<BoardReport> findByBoardIdAndUserId(Long boardId, Long userId);
}
