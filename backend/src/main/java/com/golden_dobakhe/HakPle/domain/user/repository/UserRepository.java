package com.golden_dobakhe.HakPle.domain.user.repository;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName); //아이디 중복확인

    boolean existsByNickName(String nickName); //닉네임 중복확인

    boolean existsByPhoneNum(String phoneNum);
}