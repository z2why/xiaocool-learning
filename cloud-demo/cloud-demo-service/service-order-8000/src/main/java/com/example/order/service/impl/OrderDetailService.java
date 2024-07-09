package com.example.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order.mapper.OrderDetailMapper;
import com.example.order.mapper.OrderMapper;
import com.example.order.service.IOrderDetailService;
import com.example.order.service.IOrderService;
import entity.order;
import entity.orderDetail;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailService  extends ServiceImpl<OrderDetailMapper, orderDetail> implements IOrderDetailService {
}
