package com.golden_dobakhe.HakPle.domain.user.entity;

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
public class Academy extends BaseEntity {
    @Column(length = 255, nullable = false)
    private String academyName; // 학원 이름
}