package com.golden_dobakhe.HakPle.domain.post.post.service;

import com.golden_dobakhe.HakPle.domain.post.post.entity.Hashtag;
import com.golden_dobakhe.HakPle.domain.post.post.repository.HashtagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;


    @Transactional
    public Hashtag registerHashtag(String hashtagName, String academyCode) {
        return hashtagRepository.findByHashtagNameAndAcademyCode(hashtagName, academyCode)
                .orElseGet(() -> {
                    Hashtag hashtag = new Hashtag();
                    hashtag.setHashtagName(hashtagName);
                    hashtag.setAcademyCode(academyCode);
                    return hashtagRepository.save(hashtag);
                });
    }


    @Transactional
    public List<Hashtag> registerHashtags(List<String> hashtagNames, String academyCode) {
        List<Hashtag> hashtags = new ArrayList<>();
        for (String name : hashtagNames) {
            hashtags.add(registerHashtag(name, academyCode));
        }
        return hashtags;
    }


    public List<Hashtag> getHashtagsByAcademy(String academyCode) {
        return hashtagRepository.findByAcademyCode(academyCode);
    }


    public List<Hashtag> searchHashtags(String keyword, String academyCode) {
        return hashtagRepository.findByHashtagNameContainingAndAcademyCode(keyword, academyCode);
    }
}
