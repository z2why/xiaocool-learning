package com.example.comment.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.comment.mapper.CommentMapper;
import com.example.comment.service.impl.CommentServiceimpl;
import entity.comment;
import entity.stuCou;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import vo.Result;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentServiceimpl commentService;

    @Resource
    private CommentMapper commentMapper;

    //查找所有评论信息
    @GetMapping
    public Result findAll(){
        return Result.success(commentService.list());
    }

    //保存与更新评论
    @PostMapping
    public Result save(@RequestBody comment comment ){
        return Result.success(commentService.saveOrUpdate(comment));
    }

    //删除数据
    @DeleteMapping("/{cId}")
    public Result delete(@PathVariable Integer cId){
        return Result.success(commentService.removeById(cId));
    }

    //删除数据
    @GetMapping("/delete")
    public Result deleteone(@RequestParam Integer cId){
        return Result.success(commentService.removeById(cId));
    }



    //根据ID查询评论
    @GetMapping("/{cId}")
    public Result findOne(@PathVariable Integer cId){
        return Result.success(commentService.getById(cId));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String cId,
            @RequestParam(defaultValue = "") String role,
            @RequestParam(defaultValue = "") String infoId
//          @RequestParam(defaultValue = "") String couChileType
    ){
        IPage<comment> page = new Page<>(pageNum,pageSize);
        QueryWrapper<comment> queryWarpper =new QueryWrapper<>();
        if (!"".equals(cId)){
            queryWarpper.eq("cou_id",cId);
        }
        if (!"".equals(infoId)){
            queryWarpper.eq("info_id",infoId);
        }
        if (!"".equals(role)){
            queryWarpper.eq("role",role);
        }
//        if (!"".equals(couType)){
//            queryWarpper.eq("cou_type",couType);
//            if (!"".equals(couChileType)){
//                queryWarpper.eq("cou_chile_type",couChileType);
//            }
//
//        }
        queryWarpper.select().orderByDesc("c_date");
        return Result.success(commentService.page(page,queryWarpper));
    }



    //根据学生信息查课程或根据课程信息查学生
    @GetMapping("/commentPage")
    public Result commentPage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String cId,
            @RequestParam(defaultValue = "") String role,
            @RequestParam(defaultValue = "") String infoId
    ) {

        return Result.success(commentService.commentPage(pageNum, pageSize, cId, role,infoId));
    }


}
