package com.example.comment.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.comment;
import entity.cousType;
import entity.topicQuestion;
import entity.tqUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface topicQuestionMapper extends BaseMapper<topicQuestion> {

    IPage<tqUserDTO> tqUserPage(Page<tqUserDTO> page, @Param(Constants.WRAPPER) Wrapper<tqUserDTO> wrapper);


}
