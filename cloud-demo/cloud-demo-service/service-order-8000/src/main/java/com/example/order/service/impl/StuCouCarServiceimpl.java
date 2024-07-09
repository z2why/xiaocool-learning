package com.example.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.order.mapper.StuCouCarMapper;
import com.example.order.service.IStuCouCarService;
import entity.CouCarDTO;
import entity.cousType;
import entity.orderDetailDTO;
import entity.stuCouCar;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StuCouCarServiceimpl extends ServiceImpl<StuCouCarMapper, stuCouCar> implements IStuCouCarService {

    @Resource
    private StuCouCarMapper stuCouCarMapper;
    public IPage<CouCarDTO>
    findCouCar(int pageNum, int pageSize, String id,String couName,String stuId, String couId, String couPrice
    ){
        QueryWrapper<CouCarDTO> wrapper  = new QueryWrapper<>();
        Page<CouCarDTO> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(id)) {
            wrapper.eq("scc.id",id);
        }
        if (!"".equals(couName)) {
            wrapper.eq("c.cou_name",couName);
        }

        if (!"".equals(stuId)) {
            wrapper.eq("scc.stu_id",stuId);
        }

        if (!"".equals(couId)) {
            wrapper.eq("c.cou_id",couId);
        }

        if (!"".equals(couPrice)) {
            wrapper.eq("c.cou_price",couPrice);
        }

        wrapper.select().orderByDesc("scc.add_time");
        return stuCouCarMapper.findCouCar(page, wrapper);
    }



}
