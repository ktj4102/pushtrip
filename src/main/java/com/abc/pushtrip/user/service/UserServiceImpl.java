package com.abc.pushtrip.user.service;

import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;


@Service
@Transactional// UserDetailsService 을 추가하여 시큐리티 사용 가능
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private User user;

    @Override
    public User getUserById(String userId) {

        return userRepository.findById(userId).orElse(null);
    }


    @Override
    public void createUser(User user) {
        // userId 중복 체크
        if (isUserIdDuplicate(user.getUserId())) {
            throw new IllegalArgumentException("User ID is already taken.");
        }
        // 비밀번호 인코딩
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    //사진 수정
    @Override
    public void updateUser(String userId, User user, MultipartFile newPicture) throws IOException {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!newPicture.isEmpty()) {
            findUser.setPicture(newPicture.getBytes());
        }
        findUser.setUpdateDate(new Date());
        userRepository.save(findUser);

    }

    //이름 수정
    @Override
    public void updateUserN(String userId, User user) throws IOException {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        findUser.setName(user.getName());
        findUser.setUpdateDate(new Date());
        userRepository.save(findUser);

    }

    //전화번호 수정
    @Override
    public void updateUserT(String userId, User user) throws IOException {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        findUser.setTel(user.getTel());
        findUser.setUpdateDate(new Date());
        userRepository.save(findUser);

    }

    //이메일 수정
    @Override
    public void updateUserE(String userId, User user) throws IOException {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        findUser.setEmail(user.getEmail());
        findUser.setUpdateDate(new Date());
        userRepository.save(findUser);

    }

    //생년월일 수정
    @Override
    public void updateUserB(String userId, User user) throws IOException {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        findUser.setBirth(user.getBirth());
        findUser.setUpdateDate(new Date());
        userRepository.save(findUser);

    }

    //주소 수정
    @Override
    public void updateUserA(String userId, User user) throws IOException {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        findUser.setZoneCode(user.getZoneCode());
        findUser.setJibunAddr(user.getJibunAddr());
        findUser.setRoadAddr(user.getRoadAddr());
        findUser.setRoadDetail(user.getRoadDetail());
        findUser.setUpdateDate(new Date());
        userRepository.save(findUser);

    }

    //비밀번호 수정
    @Override
    public void updateUserP(String userId, User user) throws IOException {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        findUser.setPassword(encodedPassword);
        findUser.setUpdateDate(new Date());
        userRepository.save(findUser);


    }


    @Override
    public void deleteUser(User user) {
        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        System.out.println("딜리트,함수진입>>>>"+existingUser);

        if (existingUser.isPresent()) {
            // 엔티티가 존재하면 삭제
            userRepository.delete(user);
            System.out.println("딜리트 완료");
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("해당 ID의 엔티티가 존재하지 않습니다: " + user.getUserId());
        }
    }

    public boolean isUserIdDuplicate(String userId) {
        return userRepository.existsById(userId);
    }



    @Override
    public void saveImage(String userId, MultipartFile image) throws IOException {
        Optional<User> optionalUserForum = userRepository.findById(userId);
        if (optionalUserForum.isPresent()) {
            User user = optionalUserForum.get();
            user.setPicture(image.getBytes());
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }






}
