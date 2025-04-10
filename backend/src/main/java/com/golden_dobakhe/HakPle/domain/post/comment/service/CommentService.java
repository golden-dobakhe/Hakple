package com.golden_dobakhe.HakPle.domain.post.comment.service;

import com.golden_dobakhe.HakPle.domain.post.comment.CommentDeleteResult;
import com.golden_dobakhe.HakPle.domain.post.comment.dto.CommentRequestDto;
import com.golden_dobakhe.HakPle.domain.post.comment.entity.Comment;
import com.golden_dobakhe.HakPle.domain.post.comment.repository.CommentRepository;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.post.post.repository.BoardRepository;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.entity.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // 모든 public 메서드가 트랜잭션 범위 안에서 실행
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    public final CommentRepository commentRepository;
    public final BoardRepository boardRepository;
    public final UserRepository userRepository;

    //댓글 저장
    public CommentDeleteResult commentSave(CommentRequestDto commentRequestDto) {
       Board board=boardRepository.findById(commentRequestDto.getBoardId()).orElse(null);
       if(board==null){ //게시판이 존재 X
           return CommentDeleteResult.BOARD_NOT_FOUND;
       }
       User user=userRepository.findById(2L).orElse(null); //유저디테일에서 사용자 정보 가져와서 넣기
       if(user==null){ //유저 존재 X
           return CommentDeleteResult.USER_NOT_FOUND;
       }
       if(commentRequestDto.getContent() == null || commentRequestDto.getContent().trim().isEmpty()) {
            // 비어 있는 문자열
            return CommentDeleteResult.EMPTY;
       }

        Comment comment= Comment.builder()
               .board(board)
               .content(commentRequestDto.getContent())
               .user(user)
               .status(Status.ACTIVE)
               .build();
       log.info(comment.toString());
       commentRepository.save(comment);
     return CommentDeleteResult.SUCCESS;
    }


    //댓글 수정
    public CommentDeleteResult commentUpdate(CommentRequestDto commentRequestDto) {
        User user=userRepository.findById(1L).orElse(null); //유저티테일에서 사용자 정보 가져와서 넣기
        if(user==null){ //유저 존재 X
            return CommentDeleteResult.USER_NOT_FOUND;
        }
        if(commentRequestDto.getContent() == null || commentRequestDto.getContent().trim().isEmpty()) {
            // 비어 있는 문자열
            return CommentDeleteResult.EMPTY;
        }
        Comment comment=commentRepository.findById(commentRequestDto.getCommenterId()).orElse(null);
        if(comment==null){ //없는 댓글
            return CommentDeleteResult.COMMENT_NOT_FOUND;
        }
        if(comment.getUser().getId()==2) {//댓글 작성자와 요청을 보낸 사용자가 일치하면 (시큐리티 사용)
            comment.setContent(commentRequestDto.getContent());
            log.info(comment.toString());
            return CommentDeleteResult.SUCCESS;
        }else{
            return CommentDeleteResult.UNAUTHORIZED;
        }

    }

    //댓글 삭제
    public CommentDeleteResult commentDelete(Long commenterId) {
        User user=userRepository.findById(1L).orElse(null); //유저티테일에서 사용자 정보 가져와서 넣기
        if(user==null){ //유저 존재 X
            return CommentDeleteResult.USER_NOT_FOUND;
        }
        Comment comment=commentRepository.findById(commenterId).orElse(null);
        if(comment==null){ //없는 댓글
            return CommentDeleteResult.COMMENT_NOT_FOUND;
        }
        if(comment.getUser().getId()==2) {//댓글 작성자와 요청을 보낸 사용자가 일치하면 (시큐리티 사용)
            comment.setStatus(Status.INACTIVE);
            log.info(comment.toString());
            return CommentDeleteResult.SUCCESS;
        }else{
            return CommentDeleteResult.UNAUTHORIZED;
        }
    }

}
