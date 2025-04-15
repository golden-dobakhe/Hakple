package com.golden_dobakhe.HakPle.domain.user.entity;

import com.golden_dobakhe.HakPle.domain.resource.image.entity.Image;
import com.golden_dobakhe.HakPle.global.entity.BaseEntity;
import com.golden_dobakhe.HakPle.global.entity.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
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
public class User extends BaseEntity {
    @Column(length = 100, nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String socialProvider;

    @Column(nullable = false)
    private String nickName;

    @Column(length = 20, nullable = false)
    private String phoneNum;

    @Column(length = 100)
    private String academyId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "academy_id")
//    private Academy academy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // ENUM('active', 'inactive', 'pending', 'admin')

    @Column(length = 255)
    private String refreshToken; // JWT 리프레시 토큰

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_image_id", referencedColumnName = "id")
    private Image profileImage; // 프로필 이미지 (Image 엔티티와 연결)
}
