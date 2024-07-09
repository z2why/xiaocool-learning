package com.example.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.course.mapper.CouAppraiseMapper;
import com.example.course.service.impl.CouAppraiseServiceimpl;

import entity.courseAppraise;

import org.springframework.web.bind.annotation.*;
import vo.Result;

import javax.annotation.Resource;


@RestController
@RequestMapping("/couAppraise")
public class CouAppraiseController {
    @Resource
    private CouAppraiseServiceimpl couAppraiseService;

    //查找所有订单
    @GetMapping
    public Result findAll(){
        return Result.success(couAppraiseService.list());
    }

    @Resource
    private CouAppraiseMapper couAppraiseMapper;

    //删除评价
    @GetMapping("/deleteCouApp")
    public Result delete(@RequestParam Long id){
        return Result.success(couAppraiseService.removeById(id));
    }


    @PostMapping
    public Result save(@RequestBody courseAppraise couAppraise){
        return Result.success(couAppraiseService.saveOrUpdate(couAppraise));
    }

    //删除数据
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(couAppraiseService.removeById(id));
    }

    //根据ID查询评论
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(couAppraiseService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String couAppraise
    ){
        IPage<courseAppraise> page = new Page<>(pageNum,pageSize);
        QueryWrapper<courseAppraise> queryWarpper =new QueryWrapper<>();
        if (!"".equals(id)){
            queryWarpper.eq("id",id);
        }

        if (!"".equals(couId)){
            queryWarpper.eq("cou_id",couId);
        }

        if (!"".equals(stuId)){
            queryWarpper.eq("stu_id",stuId);
        }
        if (!"".equals(couAppraise)){
            queryWarpper.like("cou_appraise",couAppraise);
        }
        return Result.success(couAppraiseService.page(page,queryWarpper));
    }

    //根据学生信息查课程或根据课程信息查学生
    @GetMapping("/findStuCouApp")
    public Result findStuCouApp(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String couId
    ) {

        return Result.success(couAppraiseService.findStuCouApp(pageNum, pageSize, stuId, couId));
    }





}
