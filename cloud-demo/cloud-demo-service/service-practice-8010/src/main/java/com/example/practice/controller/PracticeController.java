package com.example.practice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.practice.mapper.PracticeMapper;
import com.example.practice.mapper.StuPracticeMapper;
import com.example.practice.service.impl.PracticeServiceimpl;
import com.example.practice.service.impl.StuPracticeServiceimpl;
import entity.couClass;
import entity.practice;
import entity.practiceStu;
import entity.stuPractice;
import org.springframework.web.bind.annotation.*;
import vo.Constants;
import vo.Result;

import javax.annotation.Resource;

@RestController
@RequestMapping("/practice")
public class PracticeController {
    @Resource
    private PracticeServiceimpl practiceService;

    @Resource
    private PracticeMapper practiceMapper;

    @Resource
    private StuPracticeServiceimpl stuPracticeServiceimpl;

    //查找所有实践
    @GetMapping
    public Result findAll(){
        return Result.success(practiceService.list());
    }




    //保存与更新实践资料-----假删除
    @PostMapping
    public Result save(@RequestBody practice practice){
        return Result.success(practiceService.saveOrUpdate(practice));
    }

    //删除数据
    @DeleteMapping("/{praId}")
    public Result delete(@PathVariable Integer praId){
        return Result.success(practiceService.removeById(praId));
    }


    //删除数据
    @GetMapping("/deleted")
    public Result deleteOne(@RequestParam Integer id){
        return Result.success(stuPracticeServiceimpl.removeById(id));
    }


    //根据ID查询实践
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(practiceService.getById(id));
    }


    //根据学生信息查实践
    @GetMapping("/findpracticepage")
    public Result selectPracticePage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String praId,
            @RequestParam(defaultValue = "") String praName,
            @RequestParam(defaultValue = "") String praSite,
            @RequestParam(defaultValue = "") String teaId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String couName,
            @RequestParam(defaultValue = "") String praTime,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String stuName,
            @RequestParam(defaultValue = "") String audit,
            @RequestParam(defaultValue = "") String deleted

            ) {
        return Result.success(practiceService.selectPracticePage(pageNum,pageSize,praId,praName,praSite, teaId, couId, couName,praTime,stuId,stuName,audit,deleted));
    }

    //查询所有实践信息
    @GetMapping("/findpractice")
    public Result PracticePage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String praId,
            @RequestParam(defaultValue = "") String praName,
            @RequestParam(defaultValue = "") String praSite,
            @RequestParam(defaultValue = "") String teaId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String couName,
            @RequestParam(defaultValue = "") String praTime,
            @RequestParam(defaultValue = "") String deleted

    ) {
        return Result.success(practiceService.PracticePage(pageNum,pageSize,praId,praName,praSite, teaId, couId, couName,praTime,deleted));
    }

    @PostMapping("/auditPractice")
    public Result auditPractice(
            @RequestBody practiceStu stuPractice
    ){
        Long id=stuPractice.getId();
        int audit =stuPractice.getAudit();
        QueryWrapper<practiceStu> queryWarpper =new QueryWrapper<>();
        queryWarpper.eq("id",id);
        stuPractice=  stuPracticeServiceimpl.getOne(queryWarpper);
        if (stuPractice!=null){
            stuPractice.setId(id);
            stuPractice.setAudit(audit);
            stuPracticeServiceimpl.saveOrUpdate(stuPractice);
            return Result.success("审核成功！");
        }
       return Result.error(Constants.CODE_400, "审核失败");
    }

    @GetMapping("/addPractice")
    public Result addPractice(
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String praId
    ){
        QueryWrapper<practiceStu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stu_id",stuId);
        queryWrapper.eq("pra_id",praId);
        practiceStu stuPractice = stuPracticeServiceimpl.getOne(queryWrapper);
        if (stuPractice!=null){
            if (stuPractice.getAudit()==0){
                stuPractice.setAudit(3);
                stuPracticeServiceimpl.saveOrUpdate(stuPractice);
                return Result.success("取消申请成功!");
            }
            if (stuPractice.getAudit()==3){
                stuPractice.setAudit(0);
                stuPracticeServiceimpl.saveOrUpdate(stuPractice);
                return Result.success("申请成功!");
            }

        }
        else {
            stuPractice stuPractice1=new stuPractice();
            stuPractice1.setStuId(Integer.parseInt(stuId));
            stuPractice1.setPraId(Integer.parseInt(praId));
            practiceMapper.addPractice(stuPractice1);
            return Result.success("添加成功!");
        }
        return Result.error(Constants.CODE_400, "申请失败！");

    }


//    //分页查询
//    @GetMapping("/page")
//    public Result findPage(
//            @RequestParam Integer pageNum,
//            @RequestParam Integer pageSize,
//            @RequestParam(defaultValue = "") String praId,
//            @RequestParam(defaultValue = "") String praName,
//            @RequestParam(defaultValue = "") String praSite,
//            @RequestParam(defaultValue = "") String teaId,
//            @RequestParam(defaultValue = "") String couId,
//            @RequestParam(defaultValue = "") String deleted,
//            @RequestParam(defaultValue = "") String praTime
//            ){
//        IPage<practice> page = new Page<>(pageNum,pageSize);
//        QueryWrapper<practice> queryWarpper =new QueryWrapper<>();
//        if (!"".equals(praId)){
//            queryWarpper.eq("pra_id",praId);
//        }
//        if (!"".equals(praName)){
//            queryWarpper.like("pra_name",praName);
//        }
//        if (!"".equals(praSite)){
//            queryWarpper.eq("pra_site",praSite);
//        }
//        if (!"".equals(couId)){
//            queryWarpper.eq("cou_id",couId);
//        }
//        if (!"".equals(teaId)){
//            queryWarpper.eq("tea_id",teaId);
//        }
//
//        if (!"".equals(praTime)){
//            queryWarpper.eq("pra_time",praTime);
//        }
//
//        if (!"".equals(deleted)){
//            queryWarpper.eq("deleted",deleted);
//        }
//        return Result.success(practiceService.page(page,queryWarpper));
//    }


}
