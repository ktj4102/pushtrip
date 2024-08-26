package com.abc.pushtrip.user.repository;

import com.abc.pushtrip.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email); //email로 사용자 정보를 가져옴

    Optional<User> findById(String userId);

    User findByTel(String tel);

    User findByUserId(String userId);
}
