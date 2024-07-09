package com.example.progress.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.StuProgressDTO;
import entity.learningProgress;
import entity.orderDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ProgressMapper extends BaseMapper<learningProgress> {


    IPage<StuProgressDTO> findClassProgress(Page<StuProgressDTO> page, @Param(Constants.WRAPPER) Wrapper<StuProgressDTO> wrapper);

    IPage<StuProgressDTO> findProgress(Page<StuProgressDTO> page, @Param(Constants.WRAPPER) Wrapper<StuProgressDTO> wrapper);

}
