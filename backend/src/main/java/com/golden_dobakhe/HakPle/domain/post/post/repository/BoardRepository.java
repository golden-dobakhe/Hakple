package com.golden_dobakhe.HakPle.domain.post.post.repository;

import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.global.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByAcademyCodeAndStatus(String academyCode, Status status, Pageable pageable);

    @Query("SELECT DISTINCT b FROM Board b " +
            "LEFT JOIN b.tags t " +
            "LEFT JOIN b.user u " +
            "WHERE b.academyCode = :academyCode " +
            "AND b.status = 'ACTIVE' " +
            "AND (b.title LIKE %:keyword% " +
            "OR b.content LIKE %:keyword% " +
            "OR t.hashtag.hashtagName LIKE %:keyword% " +
            "OR u.nickName LIKE %:keyword%)")
    Page<Board> searchBoards(@Param("academyCode") String academyCode,
                             @Param("keyword") String keyword,
                             Pageable pageable);

    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN b.tags t " +
            "WHERE b.academyCode = :academyCode " +
            "AND b.status = 'ACTIVE' " +
            "AND t.hashtag.hashtagName = :tag")
    Page<Board> findByTagAndAcademyCode(@Param("academyCode") String academyCode,
                                        @Param("tag") String tag,
                                        Pageable pageable);

}
