package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user.controller.dto.UserDTO;
import entity.user;

public interface IUserService extends IService<user> {
    UserDTO login(UserDTO userDTO);

    user register(UserDTO userDTO);
}
