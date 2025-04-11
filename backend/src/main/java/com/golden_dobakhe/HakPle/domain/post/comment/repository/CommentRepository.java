package com.golden_dobakhe.HakPle.domain.post.comment.repository;

import com.golden_dobakhe.HakPle.domain.post.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
