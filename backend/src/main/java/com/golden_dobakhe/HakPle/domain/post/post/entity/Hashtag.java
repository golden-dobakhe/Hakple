package com.golden_dobakhe.HakPle.domain.post.post.entity;

import com.golden_dobakhe.HakPle.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class Hashtag extends BaseEntity {
    @Column(length=255, unique=true, nullable=false)
    String hashtagName; // 해시태그 이름 (유니크 설정됨)

    @Column(nullable = false)
    private String academyCode; // 아카데미 코드 (연관 필드)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")  // DB 컬럼명 지정
    private Board board;  // 필드명을 "board"로 통일
}
