package com.example.progress.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.progress.controller.dto.ProgressRequest;
import com.example.progress.controller.dto.SignInDto;
import com.example.progress.mapper.*;
import com.example.progress.service.impl.ProgressServiceimpl;
import entity.*;
import org.springframework.web.bind.annotation.*;
import vo.Constants;
import vo.Result;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Resource
    private SignInMapper signInMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private ProgressServiceimpl progressServiceimpl;

    @Resource
    private StuCouProgressMapper stuCouProgressMapper;

    @Resource
    private ProgressMapper progressMapper;

    @Resource
    private StuCouMapper stuCouMapper;

    //查找所有记录
    @GetMapping
    public Result findAll(){
        return Result.success(progressServiceimpl.list());
    }



    //删除记录
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(progressServiceimpl.removeById(id));
    }

    //根据ID查询记录
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(progressServiceimpl.getById(id));
    }




    //前端传来进度,学生号，课程号,章节号过来
    @GetMapping("/learningProgress")
    public Result check(@RequestParam(defaultValue = "") String stuId,
                        @RequestParam(defaultValue = "") String couId,
                        @RequestParam(defaultValue = "") String chaId,
                        @RequestParam(defaultValue = "") String classId,
                        @RequestParam(defaultValue = "") Integer pageSize,
                        @RequestParam(defaultValue = "") Integer pageNum
    )
    {
        QueryWrapper<learningProgress> queryWrapper=new QueryWrapper<>();
        if (!"".equals(couId)){
            queryWrapper.eq("cou_id",couId);
        }
        if (!"".equals(stuId)){
            queryWrapper.eq("stu_id",stuId);
        }
        if (!"".equals(chaId)){
            queryWrapper.eq("cha_id",chaId);
        }

        if (!"".equals(classId)){
        IPage<learningProgress> page = new Page<>(pageSize,pageNum);
        queryWrapper.eq("class_id",classId);
        return Result.success(progressServiceimpl.page(page,queryWrapper));
        }

        List<learningProgress> one=progressMapper.selectList(queryWrapper);
        if (one!=null){
            return Result.success(one);
        }

        return Result.success();
    }



    //奖励机制
    @PostMapping("signIn")
    public Result signIn(@RequestBody Long stuId) {
        // 查询用户是否签过到
        int reward=5;
        QueryWrapper<SignIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SignIn::getStuId, stuId);
        SignIn signIn = signInMapper.selectOne(queryWrapper);
        /*没有签过到, 直接新增*/
        if (null == signIn) {
            signInMapper.insert(new SignIn(stuId, LocalDateTime.now()));
        } else {/*签过到*/
            // 判断最后签到日期与当前日期是否超过一天
            LocalDate signInTime = signIn.getUpdateTime().toLocalDate();
            LocalDate currTime = LocalDate.now();
            long daysDiff = ChronoUnit.DAYS.between(signInTime, currTime);
            if (daysDiff <= 0) {
                return Result.error(Constants.CODE_600,"请勿重复签到");
            }
            if (daysDiff > 1) {
                // 1, 超过一天, 把连续签到的天数重置为 1
                signIn.setContinueDays(1);
            } else {
                // 2, 没有超过一天, 把连续签到的天数+1
                reward=10;
                signIn.setContinueDays(signIn.getContinueDays() + 1);
            }
            signIn.setUpdateTime(LocalDateTime.now());
            signInMapper.updateById(signIn);
        }
        student student=studentMapper.selectById(stuId);
        int stuScore = student.getStuScore();
        student.setStuScore(stuScore+reward);
        studentMapper.updateById(student);
        SignIn signIn1 = signInMapper.selectOne(queryWrapper);
        return Result.success(signIn1);
    }


    @GetMapping("signInList")
    public Result signInList(@RequestParam Long stuId) {
        QueryWrapper<SignIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SignIn::getStuId, stuId);
        SignIn signIn = signInMapper.selectOne(queryWrapper);
        List<SignInDto> list = new ArrayList<>(7);

        if (null == signIn) {
            // 没有签过到
            for (int i = 1; i < 8; i++) {
                list.add(new SignInDto(i, 0,0,0));
            }
        } else {
            // 签过到
            LocalDate signInTime = signIn.getUpdateTime().toLocalDate();
            LocalDate currTime = LocalDate.now();
            long daysDiff = ChronoUnit.DAYS.between(signInTime, currTime);
            int todaySign;
            if (daysDiff <= 0) {
                todaySign=1;
            }
            else {
                todaySign=0;
            }

            Integer continueDays = signIn.getContinueDays();
            // 1, 前六天的 flag是要固定的
            if (continueDays <= 6) {
                for (int i = 1; i < 8; i++) {
                    if (i <= continueDays) {
                        list.add(new SignInDto(i, 1,continueDays,todaySign));
                    } else {
                        list.add(new SignInDto(i, 0,continueDays,todaySign));
                    }
                }
            } else {
                // 2, 6天后的签到天数要跟随日期增加
                for (int i = 5; i > -2; i--) {
                    if (i > -1) {
                        list.add(new SignInDto(continueDays - i, 1,continueDays,todaySign));
                    } else {
                        list.add(new SignInDto(continueDays + 1, 0,continueDays,todaySign));
                    }
                }
            }
        }
        return Result.success(list);
    }


    @PostMapping("/watch")
    public Result progress(@RequestBody @Valid ProgressRequest request) {
                /*   if       ((DateUtils.truncatedCompareToDateUtils.addMilliseconds(learningRecordDO.getGmtModified(),(int)SectionConstants.MIN_DELTA_DURATION), new Date(), Calendar.MILLISECOND) > 0) {
                throw new BizException("访问过于频繁。");
        }*/
        progressServiceimpl.progress(request);
        return Result.success(request.getTime());
    }



    //查找所有记录
    @GetMapping("/findCouProgress")
    public Result findCouProgress(@RequestParam(defaultValue = "") String stuId,
                                  @RequestParam(defaultValue = "") String couId){
        QueryWrapper<stuCouProgress> queryWrapper=new QueryWrapper<>();
        if (!"".equals(couId)){
            queryWrapper.eq("cou_id",couId);
        }

        if (!"".equals(stuId)){
            queryWrapper.eq("stu_id",stuId);
        }
        return Result.success(stuCouProgressMapper.selectList(queryWrapper));
    }


    //查进度
    @GetMapping("/findClassProgress")
    public Result findClassProgress(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String classId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String deleted,
            @RequestParam(defaultValue = "") String couType,
            @RequestParam(defaultValue = "") String couChileType,
            @RequestParam(defaultValue = "") String learnedStatus,
            @RequestParam(defaultValue = "") String couName
//            @RequestParam(defaultValue = "") String orderFormDate,
//            @RequestParam(defaultValue = "") String orderFormP     //订单完成情况

    ) {

        return Result.success(progressServiceimpl.findClassProgress(pageNum, pageSize, stuId,classId, couId,deleted,couType,couChileType,learnedStatus,couName));
    }


    //查进度
    @GetMapping("/findProgress")
    public Result findProgress(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String deleted,
            @RequestParam(defaultValue = "") String couType,
            @RequestParam(defaultValue = "") String couChileType,
            @RequestParam(defaultValue = "") String learnedStatus,
            @RequestParam(defaultValue = "") String couName
//            @RequestParam(defaultValue = "") String orderFormDate,
//            @RequestParam(defaultValue = "") String orderFormP     //订单完成情况

    ) {

        return Result.success(progressServiceimpl.findProgress(pageNum, pageSize, stuId, couId,deleted,couType,couChileType,learnedStatus,couName));
    }


    @GetMapping("/CouData")
    public Result CouData(@RequestParam String stuId){
        LocalDateTime localDate = LocalDateTime.now();
        //时间转字符串
        String format = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        student student=studentMapper.selectById(stuId);
        Long todayTime=  student.getTodayStudyTime(); //今日学习时长
        QueryWrapper<student> queryWrapper=new QueryWrapper<>();
        Long allcont=studentMapper.selectCount(queryWrapper);
        queryWrapper.lt("today_study_time",todayTime);
        Long count= studentMapper.selectCount(queryWrapper); //超越用户数
        double sum = (float)count/allcont*100;
        java.text.DecimalFormat  df  =new   java.text.DecimalFormat("#.00");
        String sum1 = df.format(sum);
        QueryWrapper<learningProgress> Wrapper=new QueryWrapper<>();
        Wrapper.eq("stu_id",stuId);
        Wrapper.eq("learned_status","COMPALETE");
        Wrapper.like("update_time",format);
        Long learnedCha=progressServiceimpl.count(Wrapper);//看完了多少章节*5奖励积分

        QueryWrapper<CouStu> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("stu_id",stuId);
        Long have=stuCouMapper.selectCount(queryWrapper1);//拥有的课程数

        QueryWrapper<stuCouProgress> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("stu_id",stuId);
        queryWrapper2.eq("learned_status","COMPALETE");
        Long hadlearned=stuCouProgressMapper.selectCount(queryWrapper2);//已学完的课程数

        Map<String,Object> map=new HashMap<>();
        map.put("todayTime",todayTime);
        map.put("count",sum1);
        map.put("learnedCha",learnedCha);
        map.put("have",have);
        map.put("hadlearned",hadlearned);
        return  Result.success(map);
    }



}
