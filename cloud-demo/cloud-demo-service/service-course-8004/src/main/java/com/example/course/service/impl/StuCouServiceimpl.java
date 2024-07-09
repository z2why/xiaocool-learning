package com.example.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.course.mapper.ClassCouMapper;
import com.example.course.mapper.CourseMapper;
import com.example.course.mapper.StuCouMapper;
import com.example.course.service.IClassCouService;
import com.example.course.service.IStuCouService;
import entity.ClassCou;
import entity.CouStu;
import entity.stuCou;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StuCouServiceimpl extends ServiceImpl<StuCouMapper, CouStu> implements IStuCouService {
    @Resource
    private StuCouMapper StuCouMapper;

}
