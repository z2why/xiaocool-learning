package com.example.comment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.comment.mapper.topicQuestionMapper;
import com.example.comment.service.impl.topicQuestionServiceimpl;
import entity.topicQuestion;
import org.springframework.web.bind.annotation.*;
import vo.Result;

import javax.annotation.Resource;


@RestController
@RequestMapping("/topicQuestion")
public class topicQuestionController {
    @Resource
    private topicQuestionServiceimpl topicQuestionService;

    //查找所有订单
    @GetMapping
    public Result findAll(){
        return Result.success(topicQuestionService.list());
    }

    @Resource
    private topicQuestionMapper topicQuestionMapper;


    //保存与更新字典资料-----假删除
    @PostMapping
    public Result save(@RequestBody topicQuestion topicQuestion){
        return Result.success(topicQuestionService.saveOrUpdate(topicQuestion));
    }

    //删除数据
    @DeleteMapping("/{tqId}")
    public Result delete(@PathVariable Integer tqId){
        return Result.success(topicQuestionService.removeById(tqId));
    }

    //删除数据
    @GetMapping("/delete")
    public Result deleteone(@RequestParam Integer tqId){
        return Result.success(topicQuestionService.removeById(tqId));
    }


    //根据ID查询字典
    @GetMapping("/{tq_id}")
    public Result findOne(@PathVariable Integer tqId){
        return Result.success(topicQuestionService.getById(tqId));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String tqId,
            @RequestParam(defaultValue = "") String uId,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "") String createTime,
            @RequestParam(defaultValue = "") String isQuestion,
            @RequestParam(defaultValue = "") String answeredTqId,
            @RequestParam(defaultValue = "") String truename,
            @RequestParam(defaultValue = "") String isSelect
    ){
        IPage<topicQuestion> page = new Page<>(pageNum,pageSize);
        QueryWrapper<topicQuestion> queryWarpper =new QueryWrapper<>();
        if (!"".equals(tqId)){
            queryWarpper.eq("tq_id",tqId);
        }

        if (!"".equals(uId)){
            queryWarpper.eq("u_id",uId);
        }

        if (!"".equals(content)){
            queryWarpper.like("content",content);

        }

        if (!"".equals(createTime)){
            queryWarpper.eq("create_time",createTime);
        }

        if (!"".equals(isQuestion)){
            queryWarpper.eq("is_question",isQuestion);

        }

        if (!"".equals(truename)){
            queryWarpper.eq("truename",truename);

        }
        if (!"".equals(answeredTqId)){
            queryWarpper.eq("answered_tq_id",answeredTqId);
        }

        if (!"".equals(isSelect)){
            queryWarpper.eq("is_select",isSelect);

        }
        queryWarpper.select().orderByDesc("create_time");
        return Result.success(topicQuestionService.page(page,queryWarpper));
    }


    //根据学生信息查课程或根据课程信息查学生
    @GetMapping("/tqUserPage")
    public Result tqUserPage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String tqId,
            @RequestParam(defaultValue = "") String uId,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "") String createTime,
            @RequestParam(defaultValue = "") String isQuestion,
            @RequestParam(defaultValue = "") String answeredTqId,
            @RequestParam(defaultValue = "") String truename,
            @RequestParam(defaultValue = "") String isSelect
    ) {

        return Result.success(topicQuestionService.tqUserPage(pageNum, pageSize, tqId, uId,stuId,content, createTime, isQuestion, answeredTqId, truename, isSelect));
}
}