package com.example.coustype.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.coustype.mapper.CoustypeMapper;
import com.example.coustype.service.ICourstypeService;
import entity.cousType;
import org.springframework.stereotype.Service;

@Service
public class CoustypeServiceimpl extends ServiceImpl<CoustypeMapper, cousType> implements ICourstypeService {
}
