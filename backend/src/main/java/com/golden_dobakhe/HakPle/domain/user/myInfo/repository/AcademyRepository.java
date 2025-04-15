package com.golden_dobakhe.HakPle.domain.user.myInfo.repository;

import com.golden_dobakhe.HakPle.domain.user.user.entity.Academy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyRepository extends JpaRepository<Academy, Long> {
    Optional<Academy> findByPhoneNumEndsWith(String suffix);
}
