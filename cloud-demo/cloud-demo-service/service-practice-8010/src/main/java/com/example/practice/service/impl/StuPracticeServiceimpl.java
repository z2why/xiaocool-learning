package com.example.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.practice.mapper.StuPracticeMapper;
import com.example.practice.service.IstuPracticeService;
import entity.practiceStu;
import entity.stuPractice;
import org.springframework.stereotype.Service;

@Service
public class StuPracticeServiceimpl extends ServiceImpl<StuPracticeMapper, practiceStu> implements IstuPracticeService {
}