package com.golden_dobakhe.HakPle.domain.post.comment.like.service;

import com.golden_dobakhe.HakPle.domain.post.comment.CommentResult;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.entity.Comment;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.repository.CommentRepository;
import com.golden_dobakhe.HakPle.domain.post.comment.exception.CommentException;
import com.golden_dobakhe.HakPle.domain.post.comment.like.dto.LikedCommentDto;
import com.golden_dobakhe.HakPle.domain.post.comment.like.entity.CommentLike;
import com.golden_dobakhe.HakPle.domain.post.comment.like.repository.LikeRepository;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    //좋아요 +
    public CommentResult likeComment(Long commentId) {

        Comment comment=commentRepository.findById(commentId).orElse(null);
        if(comment==null){
            return CommentResult.COMMENT_NOT_FOUND;
        }
        User user = User.builder().id(1L).build(); //임의로 만든 유저
        Long userId = user.getId();

        Optional<CommentLike> existingLike = likeRepository.findByCommentIdAndUserId(commentId, userId);
        if (existingLike.isPresent()) { //이미 같은 댓글에 좋아요를 눌렀으면
            return CommentResult.ALREADY_LIKED;
        }
        comment.setLikeCount(comment.getLikeCount()+1);
        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();
        likeRepository.save(commentLike);
        return CommentResult.SUCCESS;
    }

    //좋아요 -


    //댓글 당 좋아요 수
    public int likeConut(Long commentId) {
        Comment comment=commentRepository.findById(commentId).orElse(null);
        if(comment==null) {
            throw new CommentException(CommentResult.COMMENT_NOT_FOUND);
        }
        return comment.getLikeCount();
    }

    //회원 별 좋아요 누른 댓글
    public List<LikedCommentDto> userLikedComments(Long userId) {
        return userRepository.findById(userId)
                .map(user -> likeRepository.findByUserId(user.getId()).stream()
                        .map(CommentLike::getComment)
                        .map(comment -> new LikedCommentDto(
                                comment.getId(),
                                comment.getContent()
                        ))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    //회원 별 좋아요 누른 댓글 갯수
    public int countUserLikedComments(Long userId) {
        return userRepository.findById(userId)
                .map(user ->  likeRepository.findByUserId(user.getId()).size())
                .orElse(0);
    }


}
