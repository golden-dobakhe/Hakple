package com.golden_dobakhe.HakPle.domain.post.comment.comment.service;

import com.golden_dobakhe.HakPle.domain.post.comment.CommentResult;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.dto.CommentRequestDto;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.dto.CommentResponseDto;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.entity.Comment;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.repository.CommentRepository;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.post.post.repository.BoardRepository;


import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional // 모든 public 메서드가 트랜잭션 범위 안에서 실행
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    public final CommentRepository commentRepository;
    public final BoardRepository boardRepository;
    public final UserRepository userRepository;

    // 게시글 ID로 댓글 목록 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByBoardId(Long boardId) {
        // 게시글 존재 여부 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        
        // 게시글에 연결된 모든 댓글 조회
        List<Comment> comments = board.getComments();
        
        // 활성 상태인 댓글만 필터링하고 DTO로 변환
        return comments.stream()
                .filter(comment -> comment.getStatus() == Status.ACTIVE)
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    //댓글 저장
    public CommentResult commentSave(CommentRequestDto commentRequestDto) {
       Board board=boardRepository.findById(commentRequestDto.getBoardId()).orElse(null);
       if(board==null){ //게시판이 존재 X
           return CommentResult.BOARD_NOT_FOUND;
       }
       User user=userRepository.findById(7L).orElse(null); //유저디테일에서 사용자 정보 가져와서 넣기
       if(user==null){ //유저 존재 X
           return CommentResult.USER_NOT_FOUND;
       }
       if(commentRequestDto.getContent() == null || commentRequestDto.getContent().trim().isEmpty()) {
            // 비어 있는 문자열
            return CommentResult.EMPTY;
       }

        Comment comment= Comment.builder()
               .board(board)
               .content(commentRequestDto.getContent())
               .user(user)
               .status(Status.ACTIVE)
               .build();
       log.info("댓글 저장: boardId={}, userId={}, content={}", board.getId(), user.getId(), comment.getContent());
       commentRepository.save(comment);
     return CommentResult.SUCCESS;
    }


    //댓글 수정
    public CommentResult commentUpdate(CommentRequestDto commentRequestDto) {
        User user=userRepository.findById(7L).orElse(null); //유저티테일에서 사용자 정보 가져와서 넣기
        if(user==null){ //유저 존재 X
            return CommentResult.USER_NOT_FOUND;
        }
        if(commentRequestDto.getContent() == null || commentRequestDto.getContent().trim().isEmpty()) {
            // 비어 있는 문자열
            return CommentResult.EMPTY;
        }
        Comment comment=commentRepository.findById(commentRequestDto.getCommentId()).orElse(null);
        if(comment==null){ //없는 댓글
            return CommentResult.COMMENT_NOT_FOUND;
        }
        if(comment.getUser().getId().equals(7L)) {//댓글 작성자와 요청을 보낸 사용자가 일치하면 (시큐리티 사용)
            comment.setContent(commentRequestDto.getContent());
            log.info("댓글 수정: commentId={}, content={}", comment.getId(), comment.getContent());
            return CommentResult.SUCCESS;
        }else{
            return CommentResult.UNAUTHORIZED;
        }

    }

    //댓글 삭제
    public CommentResult commentDelete(Long commentId) {
        User user=userRepository.findById(7L).orElse(null); //유저티테일에서 사용자 정보 가져와서 넣기
        if(user==null){ //유저 존재 X
            return CommentResult.USER_NOT_FOUND;
        }
        Comment comment=commentRepository.findById(commentId).orElse(null);
        if(comment==null){ //없는 댓글
            return CommentResult.COMMENT_NOT_FOUND;
        }
        if(comment.getUser().getId().equals(7L)) {//댓글 작성자와 요청을 보낸 사용자가 일치하면 (시큐리티 사용)
            comment.setStatus(Status.INACTIVE);
            log.info("댓글 삭제: commentId={}", comment.getId());
            return CommentResult.SUCCESS;
        }else{
            return CommentResult.UNAUTHORIZED;
        }
    }

    //댓글 신고

}
