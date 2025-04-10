package com.golden_dobakhe.HakPle.domain.post.like.service;

import com.golden_dobakhe.HakPle.domain.post.like.entity.BoardLike;
import com.golden_dobakhe.HakPle.domain.post.like.repository.BoardLikeRepository;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.post.post.repository.BoardRepository;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    //private final UserRepository userRepository;

    @Transactional
    public boolean toggleLike(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        /*
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        */

        User user = new User();
        user.setId(userId);




        Optional<BoardLike> existingLike = boardLikeRepository.findByBoardAndUser(board, user);
        if (existingLike.isPresent()) {
            boardLikeRepository.delete(existingLike.get());
            return false;
        } else {
            BoardLike like = new BoardLike();
            like.setBoard(board);
            like.setUser(user);
            boardLikeRepository.save(like);
            return true;
        }
    }


    @Transactional
    public int getTotalLikes(Long boardId) {
        return boardLikeRepository.countLikesByBoardId(boardId);
    }

    @Transactional
    public int getUserTotalLikes(Long userId) {
        return boardLikeRepository.countLikesByUserId(userId);
    }



}