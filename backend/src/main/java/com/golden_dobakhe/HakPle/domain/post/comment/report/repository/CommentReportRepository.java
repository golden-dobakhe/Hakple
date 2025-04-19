package com.golden_dobakhe.HakPle.domain.post.comment.report.repository;


import com.golden_dobakhe.HakPle.domain.post.comment.comment.entity.Comment;
import com.golden_dobakhe.HakPle.domain.post.comment.report.entity.CommentReport;
import com.golden_dobakhe.HakPle.domain.post.post.entity.BoardReport;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

    boolean existsByCommentAndReporter(Comment comment, User reporter);


    @Query(value = "SELECT cr FROM CommentReport cr " +
            "JOIN FETCH cr.comment c " +
            "JOIN FETCH c.user u",
            countQuery = "SELECT count(cr) FROM CommentReport cr")
    Page<CommentReport> findAllWithUserAndComment(Pageable pageable);

    @Query("SELECT br FROM BoardReport br " +
            "JOIN FETCH br.board b " +
            "JOIN FETCH b.user u " +
            "ORDER BY u.reportedCount DESC")
    List<BoardReport> findAllOrderedByReportedCount();

    int countByCommentId(Long commentId);
}
