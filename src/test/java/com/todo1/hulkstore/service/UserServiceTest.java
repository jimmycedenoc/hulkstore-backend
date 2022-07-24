package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.CategoryEntity;
import com.todo1.hulkstore.entity.UserEntity;
import com.todo1.hulkstore.repository.CategoryRepository;
import com.todo1.hulkstore.repository.UserRepository;
import com.todo1.hulkstore.service.impl.CategoryService;
import com.todo1.hulkstore.service.impl.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        Logger LOG = LoggerFactory.getLogger("JIMM");
        LOG.warn(userEntity.getUsername());
        LOG.warn(userEntity.getPassword());
        userService.save(userEntity);

        assertEquals(1.0, userRepository.count());
    }
}
