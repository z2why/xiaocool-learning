package com.example.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order.mapper.StuCouMapper;
import com.example.order.service.IStuCouService;
import entity.CouStu;
import entity.stuCou;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StuCouServiceimpl extends ServiceImpl<StuCouMapper, CouStu> implements IStuCouService {
    @Resource
    private StuCouMapper stuCouMapper;






}
