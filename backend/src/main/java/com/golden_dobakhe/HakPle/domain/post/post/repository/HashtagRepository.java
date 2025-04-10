package com.golden_dobakhe.HakPle.domain.post.post.repository;

import com.golden_dobakhe.HakPle.domain.post.post.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByHashtagName(String hashtagName);
    List<Hashtag> findByHashtagNameContainingAndAcademyCode(String keyword, String academyCode);

    Optional<Hashtag> findByHashtagNameAndAcademyCode(String hashtagName, String academyCode);
    List<Hashtag> findByAcademyCode(String academyCode);
}
