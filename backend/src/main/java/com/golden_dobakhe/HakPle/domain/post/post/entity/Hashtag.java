package com.golden_dobakhe.HakPle.domain.post.post.entity;

import com.golden_dobakhe.HakPle.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Hashtag extends BaseEntity {
    @Column(length=255, unique=true, nullable=false)
    String hashtagName; // 해시태그 이름 (유니크 설정됨)

    @Column(nullable = false)
    private String academyCode; // 학원 코드로 구분

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TagMapping> tagMappings = new ArrayList<>();

}
