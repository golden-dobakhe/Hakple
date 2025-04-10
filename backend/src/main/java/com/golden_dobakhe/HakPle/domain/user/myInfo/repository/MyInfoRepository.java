package com.golden_dobakhe.HakPle.domain.user.myInfo.repository;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyInfoRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
