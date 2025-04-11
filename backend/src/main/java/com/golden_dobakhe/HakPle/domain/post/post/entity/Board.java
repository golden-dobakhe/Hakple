package com.golden_dobakhe.HakPle.domain.post.post.entity;

import com.golden_dobakhe.HakPle.domain.post.post.exception.BoardException;
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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardLike> boardLikes = new ArrayList<>(); //  좋아요 수


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TagMapping> tags = new ArrayList<>(); // 태그 매핑 리스트


    public void increaseViewCount() {
        this.viewCount++;
    }

    public void update(String title, String content) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }

    public void validateUser(Long userId) {
        if (!this.user.getId().equals(userId)) {
            throw BoardException.unauthorized();
        }
    }

    public void validateStatus() {
        if (this.status != Status.ACTIVE) {
            throw BoardException.notFound();
        }
    }
}
