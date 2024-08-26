package com.abc.pushtrip.user.service;

import com.abc.pushtrip.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public interface UserService {

    User getUserById(String userId);
    void createUser(User user);
    void updateUser(String userId, User user, MultipartFile newPicture)throws IOException;
    void updateUserN(String userId, User user)throws IOException;
    void updateUserT(String userId, User user)throws IOException;
    void updateUserE(String userId, User user)throws IOException;
    void updateUserB(String userId, User user)throws IOException;
    void updateUserA(String userId, User user)throws IOException;
    void updateUserP(String userId, User user)throws IOException;
    void deleteUser(User user);
    void saveImage(String userId, MultipartFile image) throws IOException;


}
