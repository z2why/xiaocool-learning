package com.example.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order.mapper.StudentMapper;
import com.example.student.service.IStudentService;
import entity.student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentServiceimpl extends ServiceImpl<StudentMapper, student> implements IStudentService {
    @Resource
    private StudentMapper studentMapper;





}
