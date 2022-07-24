package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.UserEntity;
import com.todo1.hulkstore.repository.UserRepository;
import com.todo1.hulkstore.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Test
    void save() throws Exception {
        UserService userService = new UserService(userRepository);
        UserEntity userEntity = new UserEntity("jimmy", "12345");
        userService.save(userEntity);

        assertEquals(1.0, userRepository.count());
    }
}
