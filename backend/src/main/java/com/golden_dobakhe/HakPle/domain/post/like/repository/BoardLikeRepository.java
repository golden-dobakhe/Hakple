package com.golden_dobakhe.HakPle.domain.post.like.repository;

import com.golden_dobakhe.HakPle.domain.post.like.entity.BoardLike;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.global.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike>  findByBoardAndUser(Board board, User user);

    @Query("SELECT COUNT(bl) FROM BoardLike bl WHERE bl.board.id = :boardId")
    int countLikesByBoardId(@Param("boardId") Long boardId);

    @Query("SELECT COUNT(bl) FROM BoardLike bl WHERE bl.user.id = :userId")
    int countLikesByUserId(@Param("userId") Long userId);
}


