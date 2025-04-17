package com.golden_dobakhe.HakPle.domain.user.myInfo.repository;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsernameRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickNameAndPhoneNum(String nickName, String phoneNum);
}