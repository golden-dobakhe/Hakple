package com.golden_dobakhe.HakPle.domain.user.myInfo.validator;

public class AcademyCodeValidator {
    private static final String CODE_REGEX = "^[A-Z]{3}\\d{4}[A-Z]{3}$"; // ABC1234XYZ

    public static void validateAcademyId(String academyId) {
        if (academyId == null || academyId.isBlank()) {
            throw new IllegalArgumentException("학원 코드는 공백일 수 없습니다.");
        }

        if (!academyId.matches(CODE_REGEX)) {
            throw new IllegalArgumentException("학원 코드 형식이 올바르지 않습니다. 예: ABC1234XYZ");
        }

        // 이건 사실 matches에서 이미 확인되긴 하지만, 보수적으로 분리하고 싶다면 유지해도 OK
        if (!academyId.equals(academyId.toUpperCase())) {
            throw new IllegalArgumentException("학원 코드는 대문자만 사용할 수 있습니다.");
        }
    }
}