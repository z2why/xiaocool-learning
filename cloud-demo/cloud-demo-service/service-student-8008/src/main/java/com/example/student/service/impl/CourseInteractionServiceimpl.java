package com.example.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.mapper.CourseInteractionMapper;
import com.example.student.service.ICourseInteractionService;
import entity.courseInteraction;
import org.springframework.stereotype.Service;

@Service
public class CourseInteractionServiceimpl extends ServiceImpl<CourseInteractionMapper, courseInteraction> implements ICourseInteractionService {
}