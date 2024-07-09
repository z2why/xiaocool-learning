package com.example.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.course.mapper.CourseInteractionMapper;
import com.example.course.service.ICourseInteractionService;
import entity.courseInteraction;
import org.springframework.stereotype.Service;

@Service
public class CourseInteractionServiceimpl extends ServiceImpl<CourseInteractionMapper, courseInteraction> implements ICourseInteractionService {
}
