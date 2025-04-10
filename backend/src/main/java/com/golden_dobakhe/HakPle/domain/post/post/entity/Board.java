package com.golden_dobakhe.HakPle.domain.post.post.entity;

import com.golden_dobakhe.HakPle.domain.post.like.entity.BoardLike;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.global.entity.BaseEntity;
import com.golden_dobakhe.HakPle.global.entity.Status;
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
@SuperBuilder
@ToString
public class Board extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자 (User와 연결)

    @Column(length = 255, nullable = false)
    private String title; // 제목

    @Lob // TEXT 타입
    @Column(nullable = false)
    private String content; // 내용 (TEXT 타입)

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int viewCount; // 조회수 (기본값 0)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // ENUM('active', 'inactive', 'pending')

    @Column(nullable = false)
    private String academyCode; // 학원 코드

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<BoardLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Hashtag> hashtags = new ArrayList<>();
}
