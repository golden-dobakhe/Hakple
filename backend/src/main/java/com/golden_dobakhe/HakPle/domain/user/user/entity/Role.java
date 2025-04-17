package com.golden_dobakhe.HakPle.domain.user.user.entity;

import java.util.Arrays;

public enum Role {
    ADMIN("admin"),
    USER("user"),
    MODERATOR("moderator");

    private final String value;

    Role(String value) {
        this.value = value; // Enum 값과 문자열 값을 연결
    }

    public String getValue() {
        return value; // 문자열 값을 반환
    }

    public static Role fromValue(String value) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role value: " + value));
    }
}

