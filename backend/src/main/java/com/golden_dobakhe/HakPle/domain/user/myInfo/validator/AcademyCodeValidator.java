package com.golden_dobakhe.HakPle.domain.user.myInfo.validator;

import com.golden_dobakhe.HakPle.domain.user.myInfo.exception.MyInfoErrorCode;
import com.golden_dobakhe.HakPle.domain.user.myInfo.exception.MyInfoException;

public class AcademyCodeValidator {
    private static final String CODE_REGEX = "^[A-Z]{3}\\d{4}[A-Z]{3}$"; // ABC1234XYZ

    public static void validateAcademyId(String academyId) {
        if (academyId == null || academyId.isBlank()) {
            throw new MyInfoException(MyInfoErrorCode.REQUIRED);
        }

        if (!academyId.matches(CODE_REGEX)) {
            throw new MyInfoException(MyInfoErrorCode.ACADEMYID_INVALID);
        }

        // 이건 사실 matches에서 이미 확인되긴 하지만, 보수적으로 분리하고 싶다면 유지해도 OK
        if (!academyId.equals(academyId.toUpperCase())) {
            throw new MyInfoException(MyInfoErrorCode.LOWERCASE_NOT_ALLOWED);
        }
    }
}