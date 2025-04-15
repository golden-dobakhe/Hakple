package com.golden_dobakhe.HakPle.domain.post.comment.report.service;

import com.golden_dobakhe.HakPle.domain.post.comment.CommentResult;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.entity.Comment;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.repository.CommentRepository;
import com.golden_dobakhe.HakPle.domain.post.comment.exception.CommentException;
import com.golden_dobakhe.HakPle.domain.post.comment.report.entity.CommentReport;
import com.golden_dobakhe.HakPle.domain.post.comment.report.repository.CommentReportRepository;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReportService {
    private final CommentReportRepository reportRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    //댓글 신고
    public void reportComment(Long commentId,Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentResult.COMMENT_NOT_FOUND));

        User reporter = userRepository.findById(userId)
                .orElseThrow(() -> new CommentException(CommentResult.USER_NOT_FOUND));

        // 중복 신고 방지
        boolean alreadyReported = reportRepository.existsByCommentAndReporter(comment,reporter);
        if (alreadyReported) {
            throw new CommentException(CommentResult.ALREADY_REPORT);
        }

        CommentReport report = CommentReport.builder()
                .comment(comment)
                .reporter(reporter)
                .build();

        reportRepository.save(report);
    }

}
