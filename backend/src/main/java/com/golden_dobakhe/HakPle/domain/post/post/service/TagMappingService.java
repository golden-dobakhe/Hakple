package com.golden_dobakhe.HakPle.domain.post.post.service;

import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Hashtag;
import com.golden_dobakhe.HakPle.domain.post.post.entity.TagMapping;
import com.golden_dobakhe.HakPle.domain.post.post.repository.TagMappingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagMappingService {
    private final TagMappingRepository tagMappingRepository;
    private final HashtagService hashtagService;


    @Transactional
    public void linkHashtagsToBoard(Board board, List<String> hashtagNames) {
        for (String name : hashtagNames) {
            Hashtag hashtag = hashtagService.registerHashtag(name, board.getAcademyCode());
            TagMapping mapping = new TagMapping();
            mapping.setBoard(board);
            mapping.setHashtag(hashtag);
            tagMappingRepository.save(mapping);
        }
    }


    @Transactional
    public List<Hashtag> getHashtagsByBoardId(Long boardId) {
        return tagMappingRepository.findByBoardId(boardId)
                .stream()
                .map(TagMapping::getHashtag)
                .collect(Collectors.toList());
    }
}