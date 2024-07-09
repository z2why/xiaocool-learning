package com.example.classes.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.classes;
import entity.couClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassMapper extends BaseMapper<classes> {

    IPage<couClass> selectclassCoursePage(Page<couClass> page, @Param(Constants.WRAPPER) Wrapper<couClass> wrapper);

    IPage<couClass> selectCoursePage(Page<couClass> page, @Param(Constants.WRAPPER) Wrapper<couClass> wrapper);



}
