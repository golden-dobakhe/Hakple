package com.golden_dobakhe.HakPle.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "닉네임은 필수 입력값입니다.") //특수기호_, -, . 추가해야댐
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9._-]{2,20}$",
            message = "닉네임은 한글/영문/숫자와 특수기호(_, -, .)만 사용할 수 있으며 공백 없이 2~20자여야 합니다."
    )
    private String nickName;

    @NotBlank(message = "전화번호는 필수 입력값입니다.") //양식바꿔야댐
    @Pattern(regexp = "^01[0-9]{1}-?[0-9]{3,4}-?[0-9]{4}$", message = "전화번호는 10~11자리 숫자만 입력 가능합니다.")
    private String phoneNumber;

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Size(min = 4, max = 15, message = "아이디는 최소 4자 최대 15자까지 입력 가능합니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상 15자까지 입력 가능합니다.")
    private String password;
}
