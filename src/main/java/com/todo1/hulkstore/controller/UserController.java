package com.todo1.hulkstore.controller;

import com.todo1.hulkstore.entity.UserEntity;
import com.todo1.hulkstore.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userController")
@RequestMapping("/api/user")
public class UserController {
    private HttpStatus status = HttpStatus.OK;

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Lazy
    IUserService userService;

    @PostMapping
    @RequestMapping("/singup")
    ResponseEntity<UserEntity> singUp(@RequestBody UserEntity user) {
        UserEntity result = null;
        try {
            result = userService.save(user);
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in save", e);
        }

        return new ResponseEntity<>(result, status);
    }

    @PostMapping
    @RequestMapping("/login")
    ResponseEntity<UserEntity> login(@RequestBody UserEntity user) {
        UserEntity result = null;
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            result = userService.login(user);
            if (result != null && result.getUsername() != null) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.UNAUTHORIZED;
                responseHeaders.set("mes",
                        "Usuario o contraseña incorrectos");
                result = null;
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in save", e);
        }

        return new ResponseEntity<>(result, responseHeaders, status);
    }
}
