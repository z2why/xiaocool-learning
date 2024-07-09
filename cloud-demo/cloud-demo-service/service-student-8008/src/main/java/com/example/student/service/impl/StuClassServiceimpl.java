package com.example.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.mapper.StuClassMapper;
import com.example.student.service.IStuClassService;
import entity.ClassStu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class StuClassServiceimpl extends ServiceImpl<StuClassMapper, ClassStu> implements IStuClassService {


}
