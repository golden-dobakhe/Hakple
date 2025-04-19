package com.golden_dobakhe.HakPle.domain.user.admin.service;

import com.golden_dobakhe.HakPle.domain.post.comment.CommentResult;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.entity.Comment;
import com.golden_dobakhe.HakPle.domain.post.comment.comment.repository.CommentRepository;
import com.golden_dobakhe.HakPle.domain.post.comment.exception.CommentException;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.post.post.exception.BoardException;
import com.golden_dobakhe.HakPle.domain.post.post.repository.BoardRepository;
import com.golden_dobakhe.HakPle.domain.user.admin.dto.AcademyRequestDto;
import com.golden_dobakhe.HakPle.domain.user.admin.dto.AdminLoginDto;
import com.golden_dobakhe.HakPle.domain.user.admin.dto.AdminRegisterDto;
import com.golden_dobakhe.HakPle.domain.user.exception.UserErrorCode;
import com.golden_dobakhe.HakPle.domain.user.exception.UserException;
import com.golden_dobakhe.HakPle.domain.user.user.entity.Academy;
import com.golden_dobakhe.HakPle.domain.user.user.entity.Role;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.repository.AcademyRepository;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.Status;
import com.golden_dobakhe.HakPle.security.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final AcademyRepository academyRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public String registerAdmin(AdminRegisterDto dto) {
        if (userRepository.existsByUserName(dto.getUserName())) {
            throw new UserException(UserErrorCode.USERNAME_DUPLICATE);
        }
        // 전화번호 중복 확인
        if (userRepository.existsByPhoneNum(dto.getPhoneNumber())) {
            throw new UserException(UserErrorCode.PHONENUM_DUPLICATE);
        }

        User admin = User.builder()
                .userName(dto.getUserName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickName(dto.getNickName())
                .phoneNum(dto.getPhoneNumber())
                .roles(new HashSet<>(Set.of(Role.ADMIN)))
                .status(Status.ACTIVE)
                .build();

        userRepository.save(admin);
        return "관리자 등록 완료";
    }

    public Map<String, String> loginAdmin(AdminLoginDto dto) {
        User admin = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            throw new UserException(UserErrorCode.WRONG_CURRENT_PASSWORD);
        }

        if (!admin.getRoles().contains(Role.ADMIN)) {
            throw new UserException(UserErrorCode.FORBIDDEN);
        }

        String accessToken = jwtTokenizer.createAccessToken(
                admin.getId(),
                admin.getUserName(),
                admin.getNickName(),
                admin.getPhoneNum(),
                admin.getStatus(),
                admin.getRoles()
        );

        String refreshToken = jwtTokenizer.createRefreshToken(
                admin.getId(),
                admin.getUserName(),
                admin.getNickName(),
                admin.getPhoneNum(),
                admin.getStatus(),
                admin.getRoles()
        );

        // RefreshToken 저장
        admin.setRefreshToken(refreshToken);
        userRepository.save(admin);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    private String generateAcademyCode(String phoneNum) {
        // 전화번호 뒷자리 4자리 추출
        String phoneSuffix = phoneNum.replaceAll("\\D", ""); // 숫자만 남기기
        if (phoneSuffix.length() < 4) {
            throw new IllegalArgumentException("전화번호가 너무 짧습니다.");
        }
        phoneSuffix = phoneSuffix.substring(phoneSuffix.length() - 4); // 마지막 4자리

        String prefix = getRandomLetters(3);
        String suffix = getRandomLetters(3);

        return prefix + phoneSuffix + suffix;
    }

    private String getRandomLetters(int length) {
        return new Random().ints('A', 'Z' + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    //학원 생성
    public String createAcademy(AcademyRequestDto requestDto) {
        String code = generateAcademyCode(requestDto.getPhone());

        // 중복 방지 로직
        while (academyRepository.existsByAcademyCode(code)) {
            code = generateAcademyCode(requestDto.getPhone());
        }

        Academy academy = Academy.builder()
                .academyName(requestDto.getName())
                .phoneNum(requestDto.getPhone())
                .academyCode(code)
                .build();

        academyRepository.save(academy);
        return code;
    }
    //게시물 삭제 처리
    public void setBoardPending(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardException.notFound("해당 게시물이 존재하지 않습니다"));
        board.setStatus(Status.PENDING);
    }
    //댓글 삭제 처리
    public void setCommentPending(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentResult.COMMENT_NOT_FOUND));
        comment.setStatus(Status.PENDING);
    }
}
