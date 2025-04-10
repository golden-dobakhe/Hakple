package com.golden_dobakhe.HakPle.domain.post.post.repository;

import com.golden_dobakhe.HakPle.domain.post.post.entity.TagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagMappingRepository extends JpaRepository<TagMapping, Long> {
    List<TagMapping> findByBoardId(Long boardId);
}

