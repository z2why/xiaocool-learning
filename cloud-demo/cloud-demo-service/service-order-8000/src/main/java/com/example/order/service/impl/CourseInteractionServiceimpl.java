package com.example.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order.mapper.CourseInteractionMapper;
import com.example.order.service.ICourseInteractionService;
import entity.courseInteraction;
import org.springframework.stereotype.Service;

@Service
public class CourseInteractionServiceimpl extends ServiceImpl<CourseInteractionMapper, courseInteraction> implements ICourseInteractionService {
}
