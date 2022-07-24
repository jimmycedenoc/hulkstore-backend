package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.UserEntity;

public interface IUserService {
    UserEntity save(UserEntity userEntity) throws Exception;
    UserEntity login(UserEntity userEntity) throws Exception;
}
