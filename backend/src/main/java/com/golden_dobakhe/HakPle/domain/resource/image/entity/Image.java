package com.golden_dobakhe.HakPle.domain.resource.image.entity;

import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class Image extends BaseEntity {
    @ManyToOne(optional = true) // 게시글과 연결된 이미지 (nullable 가능)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToOne(mappedBy = "profileImage")
    private User user; // 유저와 연결된 프로필 이미지 (1:1 관계)

    @Lob // 대용량 데이터를 매핑할 때 사용됩니다. 주로 텍스트나 바이너리 데이터를 저장할 때 사용 , TEXT 타입
    private String filePath; // 이미지 경로 (TEXT 타입)
}