package com.example.chapter.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.StuChaCouAppDTO;
import entity.StuCouAppDTO;
import entity.courseChapterAppraise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CouChaAppraiseMapper extends BaseMapper<courseChapterAppraise> {

    List<StuChaCouAppDTO> findStuCouChaApp(@Param(Constants.WRAPPER) Wrapper<StuChaCouAppDTO> wrapper);

}
