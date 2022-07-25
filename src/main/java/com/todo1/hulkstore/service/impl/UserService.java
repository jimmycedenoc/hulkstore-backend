package com.todo1.hulkstore.service.impl;

import com.todo1.hulkstore.entity.UserEntity;
import com.todo1.hulkstore.repository.CategoryRepository;
import com.todo1.hulkstore.repository.UserRepository;
import com.todo1.hulkstore.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements IUserService {
    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder encoder;

    public UserService() {

    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(UserEntity userEntity) throws Exception {
        if(encoder instanceof BCryptPasswordEncoder) {
            String passwordEncode = encoder.encode(userEntity.getPassword());
            userEntity.setPassword(passwordEncode);
        }
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity login(UserEntity userEntity) throws Exception {

        UserEntity userSaved = userRepository.findByUsername(userEntity.getUsername());
        if (userSaved == null || !encoder.matches(userEntity.getPassword(), userSaved.getPassword())){
            return null;
        }

        return userSaved;
    }
}
