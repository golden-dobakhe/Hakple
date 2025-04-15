package com.golden_dobakhe.HakPle.domain.post.comment.like.repository;

import com.golden_dobakhe.HakPle.domain.post.comment.comment.entity.Comment;
import com.golden_dobakhe.HakPle.domain.post.comment.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<CommentLike,Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
    List<CommentLike> findByUserId(Long userId);

}
