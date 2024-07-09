package com.example.coustype.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.Files;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<Files> {
}