package com.example.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.Exception.ServiceException;
import com.example.user.controller.dto.UserDTO;
import com.example.user.mapper.StudentMapper;
import com.example.user.mapper.UserMapper;
import com.example.user.service.IUserService;
import entity.actor;
import entity.user;
import org.springframework.stereotype.Service;
import vo.Constants;
import javax.annotation.Resource;


@Service
public class UserServiceimpl extends ServiceImpl<UserMapper, user> implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private StudentMapper studentMapper;

    private static final Log LOG = Log.get();


    @Override
    public UserDTO login(UserDTO userDTO) {
        user one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            if (userDTO.getStuId()!=null){
               int stuScore= studentMapper.selectById(userDTO.getStuId()).getStuScore();
                userDTO.setStuScore(stuScore);
                return userDTO;
            }
            return userDTO;
        }
        else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");

        }

    }

    private user getUserInfo(UserDTO userDTO) {
        QueryWrapper<user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        user one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");

        }
        return one;
    }

    @Override
    public user register(UserDTO userDTO) {
        // 用户密码 md5加密
//        userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        user one = getUserInfo(userDTO);
        if (one == null) {
            one = new user();
            BeanUtil.copyProperties(userDTO, one, true);
            // 默认一个普通用户的角色
            one.setActor(actor.学生.toString());
            if (one.getUsername() == null) {
                one.setUsername(one.getUsername());
            }
            save(one);  // 把 copy完之后的用户对象存储到数据库
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }



}
