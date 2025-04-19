package com.golden_dobakhe.HakPle.domain.user.admin.service;

import com.golden_dobakhe.HakPle.domain.post.comment.report.repository.CommentReportRepository;
import com.golden_dobakhe.HakPle.domain.user.admin.dto.BoardReportDto;
import com.golden_dobakhe.HakPle.domain.user.admin.dto.CommentReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("adminCommentReportService")
@RequiredArgsConstructor
public class CommentReportService {
    private final CommentReportRepository commentReportRepository;

    public Page<CommentReportDto> getReportedComments(Pageable pageable) {
        return commentReportRepository.findAllWithUserAndComment(pageable)
                .map(report -> {
                    Long commentId = report.getComment().getId();
                    int commentReportCount = commentReportRepository.countByCommentId(commentId);
                    return CommentReportDto.fromEntity(report, commentReportCount);
                });
    }
}
