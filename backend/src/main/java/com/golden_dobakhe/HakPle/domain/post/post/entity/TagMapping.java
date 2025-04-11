package com.golden_dobakhe.HakPle.domain.post.post.entity;

import com.golden_dobakhe.HakPle.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TagMapping extends BaseEntity { // BaseEntity 상속으로 공통 필드 포함
    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board; // 게시글과 연결

    @ManyToOne(optional = false)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag; // 해시태그와 연결
}

