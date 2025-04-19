package com.golden_dobakhe.HakPle.domain.post.post.repository;

import com.golden_dobakhe.HakPle.domain.post.post.entity.BoardReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardReportRepository extends JpaRepository<BoardReport, Long> {
    Optional<BoardReport> findByBoardIdAndUserId(Long boardId, Long userId);

    @Query("SELECT br FROM BoardReport br " +
            "JOIN FETCH br.board b " +
            "JOIN FETCH b.user u")
    List<BoardReport> findAllWithUserAndBoard();

    @Query(value = "SELECT br FROM BoardReport br " +
            "JOIN FETCH br.board b " +
            "JOIN FETCH b.user u",
            countQuery = "SELECT count(br) FROM BoardReport br")
    Page<BoardReport> findAllWithUserAndBoard(Pageable pageable);

    int countByBoardId(Long boardId);
}
