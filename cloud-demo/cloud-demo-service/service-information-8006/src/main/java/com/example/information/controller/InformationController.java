package com.example.information.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.information.service.impl.InformationServiceimpl;
import entity.information;
import org.springframework.web.bind.annotation.*;
import vo.Result;
import javax.annotation.Resource;

@RestController
@RequestMapping("/information")
public class InformationController {
    @Resource
    private InformationServiceimpl InformationService;

    //查找所有资讯信息
    @GetMapping
    public Result findAll(){
        return Result.success(InformationService.list());
    }

    //保存与更新资讯资料
    @PostMapping
    public Result save(@RequestBody information information ){
        return Result.success(InformationService.saveOrUpdate(information));
    }

    //删除数据
    @DeleteMapping("/{infoId}")
    public Result delete(@PathVariable Integer infoId){
        return Result.success(InformationService.removeById(infoId));
    }

    //根据ID查询资讯
    @GetMapping("/{infoId}")
    public Result findOne(@PathVariable Integer infoId){
        return Result.success(InformationService.getById(infoId));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String infoId,
            @RequestParam(defaultValue = "") String infoTitle,
            @RequestParam(defaultValue = "") String infoAuthor,
            @RequestParam(defaultValue = "") String infoData,
            @RequestParam(defaultValue = "") String infoLike
    ){
        IPage<information> page = new Page<>(pageNum,pageSize);
        QueryWrapper<information> queryWarpper =new QueryWrapper<>();
        if (!"".equals(infoId)){
            queryWarpper.eq("info_id",infoId);
        }
        if (!"".equals(infoTitle)){
            queryWarpper.like("info_title",infoTitle);
        }
        if (!"".equals(infoAuthor)){
            queryWarpper.like("info_author",infoAuthor);
        }

       queryWarpper.select().orderByDesc("info_data");
        return Result.success(InformationService.page(page,queryWarpper));
    }



    //根据学生信息查课程或根据课程信息查学生
    @GetMapping("/infoUserPage")
    public Result infoUserPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String infoId,
            @RequestParam(defaultValue = "") String infoTitle,
            @RequestParam(defaultValue = "") String infoAuthor,
            @RequestParam(defaultValue = "") String infoData,
            @RequestParam(defaultValue = "") String infoLike
    ) {

        return Result.success(InformationService.infoUserPage(pageNum, pageSize,infoId,infoTitle,infoAuthor,infoData,infoLike));
    }
}
