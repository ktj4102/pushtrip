package com.abc.pushtrip.user.repository;

import com.abc.pushtrip.user.entity.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {
    SnsUser findByUsername(String username);
    SnsUser findByEmail(String email);
}
