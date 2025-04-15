package com.golden_dobakhe.HakPle.global;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(description = "댓글 상태")
public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive"),
    PENDING("pending");

    private final String value;

    Status(String value) {
        this.value = value; // Enum 값과 문자열 값을 연결
    }

    public String getValue() {
        return value; // 문자열 값을 반환
    }

    public static Status fromValue(String value) {
        return Arrays.stream(Status.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status value: " + value));
    }
}

