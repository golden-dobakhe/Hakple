package com.golden_dobakhe.HakPle.domain.post.post.service.impl;


import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardRequest;
import com.golden_dobakhe.HakPle.domain.post.post.dto.BoardResponse;
import com.golden_dobakhe.HakPle.domain.post.post.dto.TagResponse;
import com.golden_dobakhe.HakPle.domain.post.post.entity.*;
import com.golden_dobakhe.HakPle.domain.post.post.exception.BoardException;
import com.golden_dobakhe.HakPle.domain.post.post.repository.*;
import com.golden_dobakhe.HakPle.domain.post.post.service.BoardService;

import com.golden_dobakhe.HakPle.domain.resource.image.entity.Image;
import com.golden_dobakhe.HakPle.domain.resource.image.repository.ImageRepository;

import com.golden_dobakhe.HakPle.domain.post.comment.comment.repository.CommentRepository;

import com.golden_dobakhe.HakPle.domain.user.exception.UserErrorCode;
import com.golden_dobakhe.HakPle.domain.user.exception.UserException;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final TagMappingRepository tagMappingRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardReportRepository boardReportRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public BoardResponse createBoard(BoardRequest request, Long userId) {
        validateBoardRequest(request);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> BoardException.notFound());

        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .academyCode(user.getAcademyId())
                .user(user)
                .status(Status.ACTIVE)
                .modificationTime(null)
                .build();

        board = boardRepository.save(board);

        if (request.getTags() != null) {
            for (String tagName : request.getTags()) {
                if (!StringUtils.hasText(tagName)) continue;

                Hashtag hashtag = hashtagRepository.findByHashtagNameAndAcademyCode(tagName, user.getAcademyId())
                        .orElseGet(() -> hashtagRepository.save(Hashtag.builder()
                                .hashtagName(tagName)
                                .academyCode(user.getAcademyId())
                                .build()));

                TagMapping tagMapping = TagMapping.builder()
                        .board(board)
                        .hashtag(hashtag)
                        .build();

                board.getTags().add(tagMapping);


                tagMappingRepository.save(tagMapping);
            }
        }

        List<String> imageUrls = extractImageUrls(request.getContent());
        for (String imageUrl : imageUrls) {
            Image postFile = Image.builder()
                    .board(board)
                    .filePath(imageUrl)
                    .build();
            imageRepository.save(postFile);
        }


        return createBoardResponse(board);
    }

    @Override
    @Transactional
    public BoardResponse getBoard(Long id, Boolean postView) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> BoardException.notFound());


        board.getUser().getNickName();


        Hibernate.initialize(board.getBoardLikes());


        if (postView == null || postView) {
            board.increaseViewCount();
        }

        board.validateStatus();
        boardRepository.save(board);

        return createBoardResponse(board);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getBoards(String academyCode, String sortType, Pageable pageable) {
        if (!StringUtils.hasText(academyCode)) {
            throw BoardException.invalidRequest();
        }

        if (!StringUtils.hasText(sortType)) {
            sortType = "등록일순";
        }

        return boardRepository.findByAcademyCodeAndStatus(academyCode, Status.ACTIVE, sortType, null, pageable)
                .map(board -> {
                    Hibernate.initialize(board.getBoardLikes());
                    return createBoardResponse(board);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> searchBoards(String academyCode, String keyword, String sortType, Pageable pageable) {
        if (!StringUtils.hasText(academyCode) || !StringUtils.hasText(keyword)) {
            throw BoardException.invalidRequest();
        }
        return boardRepository.searchBoards(academyCode, keyword, sortType, null, pageable)
                .map(board -> {
                    Hibernate.initialize(board.getBoardLikes());
                    return createBoardResponse(board);
                });
    }

    @Override
    @Transactional
    public BoardResponse updateBoard(Long id, BoardRequest request, Long userId) {
        validateBoardRequest(request);

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> BoardException.notFound());

        board.validateUser(userId);
        board.validateStatus();
        board.update(request.getTitle(), request.getContent());

        board.getTags().clear();

        String academyCode = userRepository.findById(userId)
                .orElseThrow(() -> BoardException.notFound()).getAcademyId();

        if (request.getTags() != null) {
            List<String> uniqueTags = request.getTags().stream()
                    .filter(StringUtils::hasText)
                    .distinct()
                    .collect(Collectors.toList());

            for (String tagName : uniqueTags) {
                Hashtag hashtag = hashtagRepository.findByHashtagNameAndAcademyCode(tagName, academyCode)
                        .orElseGet(() -> hashtagRepository.save(Hashtag.builder()
                                .hashtagName(tagName)
                                .academyCode(academyCode)
                                .build()));

                TagMapping tagMapping = TagMapping.builder()
                        .board(board)
                        .hashtag(hashtag)
                        .build();

                board.getTags().add(tagMapping);
                tagMappingRepository.save(tagMapping);
            }
        }


        List<String> imageUrls = extractImageUrls(request.getContent());
        for (String imageUrl : imageUrls) {
            Image postFile = Image.builder()
                    .board(board)
                    .filePath(imageUrl)
                    .build();
            imageRepository.save(postFile);
        }

        return createBoardResponse(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long id, Long userId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> BoardException.notFound());

        board.validateUser(userId);
        board.validateStatus();
        board.setStatus(Status.INACTIVE);
        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void toggleLike(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardException.notFound());

        board.validateStatus();

        boardLikeRepository.findByBoardIdAndUserId(boardId, userId)
                .ifPresentOrElse(
                        boardLikeRepository::delete,
                        () -> {
                            BoardLike boardLike = BoardLike.builder()
                                    .board(board)
                                    .user(userRepository.findById(userId)
                                            .orElseThrow(() -> BoardException.notFound()))
                                    .build();
                            boardLikeRepository.save(boardLike);
                        }
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getBoardsByTag(String academyCode, String tag, String sortType, Pageable pageable) {
        if (!StringUtils.hasText(academyCode) || !StringUtils.hasText(tag)) {
            throw BoardException.invalidRequest();
        }
        return boardRepository.findByTagAndAcademyCode(academyCode, tag, sortType, null, pageable)
                .map(board -> {
                    Hibernate.initialize(board.getBoardLikes());
                    return createBoardResponse(board);
                });
    }

    @Override
    @Transactional
    public void createBoardReport(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardException.notFound());

        board.validateStatus();

        User user=userRepository.findById(board.getUser().getId()).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        user.setReportedCount(user.getReportedCount() + 1); //신고 횟수 누적



        if (boardReportRepository.findByBoardIdAndUserId(boardId, userId).isPresent()) {
            throw BoardException.invalidRequest();
        }


        BoardReport boardReport = BoardReport.builder()
                .board(board)
                .user(userRepository.findById(userId)
                        .orElseThrow(() -> BoardException.notFound()))
                .build();

        boardReportRepository.save(boardReport);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getPopularTags(String academyCode) {
        return tagMappingRepository.findTop5PopularTagsByAcademyCode(academyCode)
                .stream()
                .map(tag -> TagResponse.builder()
                        .name(tag.getHashtagName())
                        .count(tag.getCount())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getPopularTags(String academyCode, Integer minLikes) {
        return tagMappingRepository.findTop5PopularTagsByAcademyCode(academyCode, minLikes)
                .stream()
                .map(tag -> TagResponse.builder()
                        .name(tag.getHashtagName())
                        .count(tag.getCount())
                        .build())
                .collect(Collectors.toList());
    }

    private BoardResponse createBoardResponse(Board board) {
        List<String> tags = board.getTags().stream()
                .map(tagMapping -> tagMapping.getHashtag().getHashtagName())
                .collect(Collectors.toList());

        int commentCount = commentRepository.countByBoardIdAndStatus(board.getId(), Status.ACTIVE);

        return BoardResponse.from(board, tags, commentCount);
    }

    private void validateBoardRequest(BoardRequest request) {
        if (!StringUtils.hasText(request.getTitle())) {
            throw BoardException.invalidRequest();
        }
        if (!StringUtils.hasText(request.getContent())) {
            throw BoardException.invalidRequest();
        }

    }

    private List<String> extractImageUrls(String content) {
        Pattern pattern = Pattern.compile("!\\[.*?\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(content);
        List<String> urls = new ArrayList<>();

        while (matcher.find()) {
            urls.add(matcher.group(1));
        }

        return urls;
    }


    @Override
    public String getAcademyCodeByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> BoardException.notFound());

        String academyCode = user.getAcademyId();
        if (!StringUtils.hasText(academyCode)) {
            throw BoardException.invalidRequest();
        }

        return academyCode;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getBoardsByUserId(Long userId, String sortType, Integer minLikes, Pageable pageable) {
        String academyCode = getAcademyCodeByUserId(userId);
        if (academyCode == null) throw BoardException.notFound();

        return boardRepository.findByAcademyCodeAndStatus(academyCode, Status.ACTIVE, sortType, minLikes, pageable)
                .map(board -> {
                    Hibernate.initialize(board.getBoardLikes());
                    return createBoardResponse(board);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> searchBoardsByUserId(Long userId, String keyword, String sortType, Integer minLikes, Pageable pageable) {
        String academyCode = getAcademyCodeByUserId(userId);
        if (academyCode == null) throw BoardException.notFound();

        return boardRepository.searchBoards(academyCode, keyword, sortType, minLikes, pageable)
                .map(board -> {
                    Hibernate.initialize(board.getBoardLikes());
                    return createBoardResponse(board);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getBoardsByTagAndUserId(Long userId, String tag, String sortType, Integer minLikes, Pageable pageable) {
        String academyCode = getAcademyCodeByUserId(userId);
        if (academyCode == null) throw BoardException.notFound();

        return boardRepository.findByTagAndAcademyCode(academyCode, tag, sortType, minLikes, pageable)
                .map(board -> {
                    Hibernate.initialize(board.getBoardLikes());
                    return createBoardResponse(board);
                });
    }


    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getPopularTagsByUserId(Long userId) {
        String academyCode = getAcademyCodeByUserId(userId);
        return getPopularTags(academyCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getPopularTagsByUserId(Long userId, Integer minLikes) {
        String academyCode = getAcademyCodeByUserId(userId);
        return getPopularTags(academyCode, minLikes);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> searchBoardsByType(String academyCode, String searchType, String keyword, String sortType, Pageable pageable) {
        if (!StringUtils.hasText(academyCode) || !StringUtils.hasText(keyword) || !StringUtils.hasText(searchType)) {
            throw BoardException.invalidRequest();
        }
        
        // 검색 유형이 유효한지 체크
        if (!searchType.equals("태그") && !searchType.equals("작성자") && !searchType.equals("제목")) {
            throw BoardException.invalidRequest();
        }
        
        return boardRepository.searchBoardsByType(academyCode, searchType, keyword, sortType, null, pageable)
                .map(board -> {
                    Hibernate.initialize(board.getBoardLikes());
                    return createBoardResponse(board);
                });
    }


    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> searchBoardsByTypeAndUserId(Long userId, String searchType, String keyword, String sortType, Integer minLikes, Pageable pageable) {
        String academyCode = getAcademyCodeByUserId(userId);
        if (academyCode == null) throw BoardException.notFound();

        return boardRepository.searchBoardsByType(academyCode, searchType, keyword, sortType, minLikes, pageable)
                .map(board -> {
                    Hibernate.initialize(board.getBoardLikes());
                    return createBoardResponse(board);
                });
    }

    @Override
    @Transactional
    public void increaseViewCount(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> BoardException.notFound());

        board.increaseViewCount();
        boardRepository.save(board);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isReportedByUser(Long boardId, Long userId) {

        return boardReportRepository.findByBoardIdAndUserId(boardId, userId).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLikedByUser(Long boardId, Long userId) {

        return boardLikeRepository.findByBoardIdAndUserId(boardId, userId).isPresent();
    }
}
