package com.golden_dobakhe.HakPle.domain.user.myInfo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MyInfoErrorCode {
    REQUIRED("필수 작성 항목입니다.", HttpStatus.BAD_REQUEST),
    PHONENUM_DUPLICATE("전화번호를 이미 사용 중입니다.", HttpStatus.CONFLICT),
    NICKNAME_DUPLICATE("닉네임을 이미 사용 중입니다.", HttpStatus.CONFLICT),
    SAME_AS_CURRENT("기존과 동일합니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_INVALID("닉네임은 한글/영문 2~20자, 공백 없이 특수 기호는 _, -, . 만 사용 가능합니다.", HttpStatus.BAD_REQUEST),
    PHONENUM_INVALID("전화번호는 숫자만 포함하며, 10~11자리여야 합니다.", HttpStatus.BAD_REQUEST),
    ACADEMYID_INVALID("학원 코드 형식이 올바르지 않습니다. 예: ABC1234XYZ", HttpStatus.BAD_REQUEST),
    LOWERCASE_NOT_ALLOWED("대문자만 사용할 수 있습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    MyInfoErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
