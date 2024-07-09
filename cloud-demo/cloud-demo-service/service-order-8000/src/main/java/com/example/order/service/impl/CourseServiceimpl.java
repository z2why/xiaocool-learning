package com.example.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.order.mapper.CourseMapper;
import com.example.order.service.ICourseService;
import entity.course;
import entity.stuCou;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceimpl extends ServiceImpl<CourseMapper, course> implements ICourseService {
    @Resource
    private CourseMapper courseMapper;




}
