package com.golden_dobakhe.HakPle.domain.post.post.repository;

import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.global.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"user", "tags", "tags.hashtag"})
    @Query("SELECT b FROM Board b WHERE b.academyCode = :academyCode AND b.status = :status " +
           "ORDER BY " +
           "CASE " +
           "   WHEN :sortType = '조회순' THEN b.viewCount " +
           "   ELSE 0 " +
           "END DESC, " +
           "CASE " +
           "   WHEN :sortType = '댓글순' THEN SIZE(b.comments) " +
           "   ELSE 0 " +
           "END DESC, " +
           "CASE " +
           "   WHEN :sortType = '좋아요순' THEN SIZE(b.boardLikes) " +
           "   ELSE 0 " +
           "END DESC, " +
           "CASE " +
           "   WHEN :sortType = '등록일순' OR :sortType NOT IN ('조회순', '댓글순', '좋아요순') THEN b.creationTime " +
           "   ELSE b.creationTime " +
           "END DESC")
    Page<Board> findByAcademyCodeAndStatus(
            @Param("academyCode") String academyCode, 
            @Param("status") Status status, 
            @Param("sortType") String sortType,
            Pageable pageable);

    @EntityGraph(attributePaths = {"user", "tags", "tags.hashtag"})
    @Query("SELECT b FROM Board b WHERE b.academyCode = :academyCode AND b.status = 'ACTIVE' " +
           "AND (LOWER(b.title) LIKE CONCAT('%', LOWER(:keyword), '%') " +
           "OR LOWER(b.content) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
           "ORDER BY " +
           "CASE " +
           "   WHEN :sortType = '조회순' THEN b.viewCount " +
           "   ELSE 0 " +
           "END DESC, " +
           "CASE " +
           "   WHEN :sortType = '댓글순' THEN SIZE(b.comments) " +
           "   ELSE 0 " +
           "END DESC, " +
           "CASE " +
           "   WHEN :sortType = '좋아요순' THEN SIZE(b.boardLikes) " +
           "   ELSE 0 " +
           "END DESC, " +
           "CASE " +
           "   WHEN :sortType = '등록일순' OR :sortType NOT IN ('조회순', '댓글순', '좋아요순') THEN b.creationTime " +
           "   ELSE b.creationTime " +
           "END DESC")
    Page<Board> searchBoards(@Param("academyCode") String academyCode,
                           @Param("keyword") String keyword,
                           @Param("sortType") String sortType,
                           Pageable pageable);

    @EntityGraph(attributePaths = {"user", "tags", "tags.hashtag"})
    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN b.tags t " +
            "WHERE b.academyCode = :academyCode " +
            "AND b.status = 'ACTIVE' " +
            "AND LOWER(t.hashtag.hashtagName) LIKE CONCAT('%', LOWER(:tag), '%') " +
            "ORDER BY " +
            "CASE " +
            "   WHEN :sortType = '조회순' THEN b.viewCount " +
            "   ELSE 0 " +
            "END DESC, " +
            "CASE " +
            "   WHEN :sortType = '댓글순' THEN SIZE(b.comments) " +
            "   ELSE 0 " +
            "END DESC, " +
            "CASE " +
            "   WHEN :sortType = '좋아요순' THEN SIZE(b.boardLikes) " +
            "   ELSE 0 " +
            "END DESC, " +
            "CASE " +
            "   WHEN :sortType = '등록일순' OR :sortType NOT IN ('조회순', '댓글순', '좋아요순') THEN b.creationTime " +
            "   ELSE b.creationTime " +
            "END DESC")
    Page<Board> findByTagAndAcademyCode(@Param("academyCode") String academyCode,
                                      @Param("tag") String tag,
                                      @Param("sortType") String sortType,
                                      Pageable pageable);

    @EntityGraph(attributePaths = {"user", "tags", "tags.hashtag"})
    @Query("SELECT DISTINCT b FROM Board b " +
            "LEFT JOIN b.tags t " +
            "WHERE b.academyCode = :academyCode " +
            "AND b.status = 'ACTIVE' " +
            "AND (" +
            "   (:searchType = '태그' AND EXISTS (SELECT 1 FROM b.tags tag WHERE LOWER(tag.hashtag.hashtagName) LIKE CONCAT('%', LOWER(:keyword), '%'))) " +
            "   OR (:searchType = '작성자' AND LOWER(b.user.nickName) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
            "   OR (:searchType = '제목' AND LOWER(b.title) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
            ") " +
            "ORDER BY " +
            "CASE " +
            "   WHEN :sortType = '조회순' THEN b.viewCount " +
            "   ELSE 0 " +
            "END DESC, " +
            "CASE " +
            "   WHEN :sortType = '댓글순' THEN SIZE(b.comments) " +
            "   ELSE 0 " +
            "END DESC, " +
            "CASE " +
            "   WHEN :sortType = '좋아요순' THEN SIZE(b.boardLikes) " +
            "   ELSE 0 " +
            "END DESC, " +
            "CASE " +
            "   WHEN :sortType = '등록일순' OR :sortType NOT IN ('조회순', '댓글순', '좋아요순') THEN b.creationTime " +
            "   ELSE b.creationTime " +
            "END DESC")
    Page<Board> searchBoardsByType(
            @Param("academyCode") String academyCode,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword,
            @Param("sortType") String sortType,
            Pageable pageable);
}
