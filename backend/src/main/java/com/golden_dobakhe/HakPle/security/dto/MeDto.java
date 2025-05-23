package com.golden_dobakhe.HakPle.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MeDto {
    private final Long id;
    private final String nickname;
    private final String userName;
    private final LocalDateTime creationTime;
    private final LocalDateTime modificationTime;
    private final String academyId;
    private final String profileImageUrl;

    public MeDto(Long id, String nickname, String userName, LocalDateTime creationTime, LocalDateTime modificationTime, String academyId, String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.userName = userName;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
        this.academyId = academyId;
        this.profileImageUrl = profileImageUrl;
    }
}
