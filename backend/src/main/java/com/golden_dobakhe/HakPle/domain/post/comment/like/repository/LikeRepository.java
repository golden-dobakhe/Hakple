package com.golden_dobakhe.HakPle.domain.post.comment.like.repository;

import com.golden_dobakhe.HakPle.domain.post.comment.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<CommentLike,Long> {
}
