package com.example.user.controller;


import cn.hutool.core.util.StrUtil;
import com.example.user.controller.dto.UserDTO;
import com.example.user.service.impl.UserServiceimpl;
import entity.user;
import org.springframework.web.bind.annotation.*;
import vo.Constants;
import vo.Result;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserServiceimpl userService;


    //查找所有用户
    @GetMapping
    public Result findAll(){
        return Result.success(userService.list());
    }


    //保存与更新
    @PostMapping
    public Result save(@RequestBody user user){
        return Result.success(userService.saveOrUpdate(user));
    }


    //登录
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "请正确输入！");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String truename = userDTO.getTruename();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)|| StrUtil.isBlank(truename)) {
            return Result.error(Constants.CODE_400, "请正确输入！");
        }
        return Result.success(userService.register(userDTO));
    }




}
