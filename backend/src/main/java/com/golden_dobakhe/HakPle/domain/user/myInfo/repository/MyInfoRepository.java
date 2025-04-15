package com.golden_dobakhe.HakPle.domain.user.myInfo.repository;

import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyInfoRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);

    boolean existsByNickName(String NickName);

    boolean existsByPhoneNum(String PhoneNum);
}
