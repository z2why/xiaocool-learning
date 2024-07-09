package com.example.information.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.comment;
import entity.infoUserDTO;
import entity.information;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InformationMapper extends BaseMapper<information> {

    IPage<infoUserDTO> infoUserPage(Page<infoUserDTO> page, @Param(Constants.WRAPPER) Wrapper<infoUserDTO> queryWarpper);

}
