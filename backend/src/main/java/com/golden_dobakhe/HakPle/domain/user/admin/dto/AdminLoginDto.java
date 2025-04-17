package com.golden_dobakhe.HakPle.domain.user.admin.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AdminLoginDto {
    private String userName;
    private String password;
}