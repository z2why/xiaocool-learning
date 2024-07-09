package com.example.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.order.mapper.StuCouCarMapper;
import com.example.order.service.impl.StuCouCarServiceimpl;
import com.example.order.service.impl.StuCouServiceimpl;
import entity.ClassCou;
import entity.CouStu;
import entity.stuCouCar;
import org.springframework.web.bind.annotation.*;
import vo.Constants;
import vo.Result;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/stuCouCar")
public class StuCouCarController {
    @Resource
    private StuCouCarServiceimpl stuCouCarService;

    @Resource
    private StuCouCarMapper stuCouCarMapper;
    @Resource
    private StuCouServiceimpl stuCouServiceimpl;

    //查找所有购物车
    @GetMapping
    public Result findAll(){
        return Result.success(stuCouCarService.list());
    }

    //保存与更新购物车
    @PostMapping
    public Result save(@RequestBody stuCouCar stuCouCar){
        int stuid=stuCouCar.getStuId();
        int couid=stuCouCar.getCouId();
        QueryWrapper<stuCouCar> qwq=new QueryWrapper<>();
        qwq.eq("stu_id",stuid);
        qwq.eq("cou_id",couid);
        stuCouCar stc= stuCouCarService.getOne(qwq);
        if (stc!=null){
            return Result.error(Constants.CODE_600, "该课程已在购物车里啦！赶紧去支付吧！");
        }
        QueryWrapper<CouStu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stu_id",stuid);
        queryWrapper.eq("cou_id",couid);
        CouStu stucou=new CouStu();
        stucou=stuCouServiceimpl.getOne(queryWrapper);
        if (stucou!=null){
         return Result.error(Constants.CODE_600, "您已购买过,无需重复购买");
        }
        return Result.success(stuCouCarService.saveOrUpdate(stuCouCar));

    }

    //删除数据
    @GetMapping("/delete")
    public Result delete(@RequestBody String id){
        return Result.success(stuCouCarService.removeById(id));
    }

    @PostMapping ("/del/batch")
    public Result deleteBatch(@RequestBody List<stuCouCar> stuCouCar1){
        List<Integer> l1 = stuCouCar1.stream().map(stuCouCar::getCouId).collect(Collectors.toList());//获取到每个班的每门课的id
        List<Integer> l2 = stuCouCar1.stream().map(stuCouCar::getStuId).collect(Collectors.toList());//获取到每个班的每门课的id
        for (Integer i:l1){
            QueryWrapper<stuCouCar> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("stu_id",l2.get(0));
            queryWrapper.eq("cou_id",i);
            stuCouCarService.remove(queryWrapper);
        }
        QueryWrapper<stuCouCar> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stu_id",l2.get(0));
        return  Result.success(stuCouCarService.list(queryWrapper));

    }

    //根据ID查询购物车
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(stuCouCarService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String couId
    ){
        IPage<stuCouCar> page = new Page<>(pageNum,pageSize);
        QueryWrapper<stuCouCar> queryWarpper =new QueryWrapper<>();
        if (!"".equals(id)){
            queryWarpper.like("id",id);
        }
        if (!"".equals(stuId)){
            queryWarpper.eq("stu_id",stuId);
        }
        if (!"".equals(couId)){
            queryWarpper.eq("cou_id",couId);
        }
        return Result.success(stuCouCarService.page(page,queryWarpper));
    }




    //查购物车详情
    @GetMapping("/findCouCar")
    public Result findCouCar(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String couName,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String couPrice

            ) {

        return Result.success(stuCouCarService.findCouCar(pageNum, pageSize,id,couName,stuId,couId,couPrice));
    }


}
