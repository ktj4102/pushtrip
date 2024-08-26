package com.abc.pushtrip.repository;

import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // 테스트용 프로파일을 사용하는 경우 설정
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserId() {
        // Given: 테스트를 위한 사용자 데이터 준비
        User user = User.builder()
                .userId("user123")
                .email("test@example.com")
                .password("test_password") // setPassword 메서드를 통해 해싱됨
                .name("Test User")
                .tel("010-1234-5678")
                .accountNo("123-456-7890")
                .gender("M")
                .birth("1990-01-01")
                .zoneCode("123456")
                .roadAddr("Test Road Address")
                .roadDetail("Test Road Detail")
                .jibunAddr("Test Jibun Address")
                .role("USER")
                .deleteYn("N")
                .insertDate(new Date())
                .updateDate(new Date())
                .build();

        user.setPassword("test_password"); // 비밀번호 해싱

        // When: 사용자 데이터를 저장
        userRepository.save(user);

        // Then: 저장된 사용자를 userId로 조회
        User foundUser = userRepository.findByUserId("user123");
        assertNotNull(foundUser, "User should not be null");
        assertEquals("user123", foundUser.getUserId());
        assertEquals("test@example.com", foundUser.getEmail());
        assertEquals("Test User", foundUser.getName());
        assertEquals("010-1234-5678", foundUser.getTel());
    }
}
