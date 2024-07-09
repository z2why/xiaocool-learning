package com.example.order.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.CouCarDTO;
import entity.orderDetailDTO;
import entity.stuCouCar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StuCouCarMapper extends BaseMapper<stuCouCar> {

    IPage<CouCarDTO> findCouCar(Page<CouCarDTO> page, @Param(Constants.WRAPPER) Wrapper<CouCarDTO> wrapper);

}
