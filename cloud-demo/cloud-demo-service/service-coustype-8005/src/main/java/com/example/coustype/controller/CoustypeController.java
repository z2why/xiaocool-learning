package com.example.coustype.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.coustype.mapper.CoustypeMapper;
import com.example.coustype.service.impl.CoustypeServiceimpl;

import entity.cousType;
import org.springframework.web.bind.annotation.*;
import vo.Result;

import javax.annotation.Resource;



@RestController
@RequestMapping("/coustype")
public class CoustypeController {
    @Resource
    private CoustypeServiceimpl coustypeService;

    //查找所有订单
    @GetMapping
    public Result findAll(){
        return Result.success(coustypeService.list());
    }

    @Resource
    private CoustypeMapper coustypeMapper;


    //保存与更新字典资料-----假删除
    @PostMapping
    public Result save(@RequestBody cousType coustype){
        return Result.success(coustypeService.saveOrUpdate(coustype));
    }

    //删除数据
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(coustypeService.removeById(id));
    }

    //根据ID查询字典
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(coustypeService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String couType,
            @RequestParam(defaultValue = "") String couParentType
    ){
        IPage<cousType> page = new Page<>(pageNum,pageSize);
        QueryWrapper<cousType> queryWarpper =new QueryWrapper<>();
        if (!"".equals(id)){
            queryWarpper.like("id",id);
        }

        if (!"".equals(couType)){
            queryWarpper.eq("cou_type",couType);

        }
        return Result.success(coustypeService.page(page,queryWarpper));
    }

}
