package com.example.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order.mapper.OrderMapper;
import com.example.order.service.IOrderService;
import entity.order;
import entity.orderDetail;
import entity.orderDetailDTO;
import entity.stuCou;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderServiceimpl extends ServiceImpl<OrderMapper, order> implements IOrderService {
    @Resource
    private OrderMapper orderMapper;


    public IPage<orderDetailDTO>
    selectOrderDetail(int pageNum, int pageSize, String orderId, String couId,
                      String uId,String username, String couName, String teaId,
                      String couPrice, String orderFormDate, String orderFormP
    ){
        QueryWrapper<orderDetailDTO> wrapper  = new QueryWrapper<>();
        Page<orderDetailDTO> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(orderId)) {
            wrapper.eq("od.order_id",orderId);
        }

        if (!"".equals(couId)) {
            wrapper.eq("c.cou_id",couId);
        }

        if (!"".equals(uId)) {
            wrapper.eq("o.u_id",uId);
        }

        if (!"".equals(couName)) {
            wrapper.eq("c.cou_name",couName);
        }
        if (!"".equals(teaId)) {
            wrapper.eq("c.tea_id",teaId);
        }

        if (!"".equals(couPrice)) {
            wrapper.eq("c.cou_price",couPrice);
        }
        if (!"".equals(orderFormDate)) {
            wrapper.eq("o.orderForm_date",orderFormDate);
        }

        if (!"".equals(orderFormP)) {
            wrapper.eq("o.orderForm_p",orderFormP);
        }

        if (!"".equals(username)) {
            wrapper.eq("u.username",username);
        }
        return orderMapper.selectOrderDetail(page, wrapper);
    }





}
