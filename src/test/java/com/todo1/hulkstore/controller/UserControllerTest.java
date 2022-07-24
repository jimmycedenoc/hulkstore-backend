package com.todo1.hulkstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo1.hulkstore.entity.UserEntity;
import com.todo1.hulkstore.security.SecurityConfig;
import com.todo1.hulkstore.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WithMockUser
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @MockBean
    private IUserService userService;

    @Test
    void singUp() throws Exception {
        UserEntity user =  new UserEntity("jimmy", encoder.encode("12345"));
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        when(userService.save(any(UserEntity.class))).thenReturn(user);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user/singup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        );

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("jimmy"));
    }

    @Test
    void loginAuthorized() throws Exception {
        UserEntity user = new UserEntity("jimmy", "12345");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        when(userService.login(any(UserEntity.class))).thenReturn(user);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jimmy"));
    }

    @Test
    void loginUnauthorized() throws Exception {
        UserEntity user = new UserEntity("jimmy", "incorrecto");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        when(userService.login(any(UserEntity.class))).thenReturn(null);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        );

        resultActions.andExpect(status().isUnauthorized());
    }
}
