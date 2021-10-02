package com.kjsp.kjspslide.repository;

import com.kjsp.kjspslide.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

//  UserInfo findUserInfoByUserEmailAndUserPassword();
}
