package com.example.progress.service.impl;


import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.progress.Exception.ServiceException;
import com.example.progress.controller.dto.ProgressRequest;
import com.example.progress.mapper.ChapterMapper;
import com.example.progress.mapper.ProgressMapper;
import com.example.progress.mapper.StuCouProgressMapper;
import com.example.progress.mapper.StudentMapper;
import com.example.progress.service.IProgressService;
import entity.*;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import vo.Constants;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProgressServiceimpl extends ServiceImpl<ProgressMapper, learningProgress> implements IProgressService {

    //默认用户
    public final String NAME = "keying";
    private static final Log LOG = Log.get();

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private ProgressServiceimpl progressServiceimpl;

    @Resource
    private ProgressMapper progressMapper;

    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private StuCouProgressMapper stuCouProgressMapper;


    @Override
    public void progress(ProgressRequest request) {
        //存入学习总时长
        progressUserLearned(request);
        //统计当前课程 & 节是否已学完
        insertUserLearnStats(request);
    }

    private void progressUserLearned(ProgressRequest request) {
        student student=new student();
        student = studentMapper.selectById(request.getStuId());
//        if (student!=null) { //不是null
            student.setTodayStudyTime(student.getTodayStudyTime()+request.getTime());//更新今日学习时长
            student.setStuStudyTime(student.getStuStudyTime()+request.getTime()); //更新总时长
            studentMapper.updateById(student);

    }






    private void insertUserLearnStats(ProgressRequest request) {
        QueryWrapper<learningProgress> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stu_id",request.getStuId());
        queryWrapper.eq("cou_id",request.getCouId());
        queryWrapper.eq("cha_id",request.getChaId());
        learningProgress learning= progressServiceimpl.getOne(queryWrapper);
        if (learning!=null){
            learningProgress learningProgress=new learningProgress();
            learningProgress.setMediaDuration(request.getMediaDuration());
            learningProgress.setMediaProgress(request.getMediaProgress());
            learningProgress.setId(learning.getId());
            boolean flag = calculateLearned(request, learningProgress.getMediaDuration(), request.getMediaProgress());
            learningProgress.setLearnedStatus("LEARNING");
            if (flag){
                learningProgress.setLearnedStatus("COMPALETE");
            }
            progressServiceimpl.saveOrUpdate(learningProgress);
            courseRedisson(Boolean.FALSE, request, learning.getLearnedStatus(),
                    learningProgress.getLearnedStatus());
        }

        else {
            learningProgress insertlearning=new learningProgress();
            insertlearning.setMediaDuration(request.getMediaDuration());
            //获取是否已学完
            boolean flag = calculateLearned(request, insertlearning.getMediaDuration(), request.getMediaProgress());
            insertlearning.setLearnedStatus("LEARNING");
            if (flag) {
                insertlearning.setLearnedStatus("COMPALETE");
            }
            insertlearning.setMediaProgress(request.getMediaProgress());
            insertlearning.setCouId(request.getCouId());
            insertlearning.setStuId(request.getStuId());
            insertlearning.setChaId(request.getChaId());
            progressServiceimpl.save(insertlearning);

            //处理课程 是否学完
            courseRedisson(Boolean.TRUE, request, null,
                    insertlearning.getLearnedStatus());
        }
    }


    private boolean calculateLearned(ProgressRequest request, Long allDuration, Long learnedDuration) {
//        if (request.getSectionType().equals("TEXT")) {
//            return Boolean.TRUE;
//        }
//        if (request.getSectionType().equals("VIDEO")) {
            if (learnedDuration >= (allDuration * 2 / 3)) {
                return Boolean.TRUE;
            }
//        }
        return Boolean.FALSE;
    }


    private void courseRedisson(Boolean aFalse, ProgressRequest request, String oldLearnedStatus,
                                String newLearnedStatus) {
        // 默认连接上127.0.0.1:6379
        RedissonClient redissonClient = Redisson.create();
        // 一个分布式锁，指明锁的名称
        RLock rLock = redissonClient.getLock(NAME + request.getCouId());
        try {
            if (rLock.tryLock(1, 1, TimeUnit.MINUTES)) {
                log.info("获取到锁");
                courseProgress(Boolean.FALSE, request, oldLearnedStatus,
                        newLearnedStatus);
            }
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        } finally {
            rLock.unlock();
        }
    }


    /**
     * @param aFalse           是否第一次进入
     * @param request
     * @param oldLearnedStatus 老的学习状态
     * @param newLearnedStatus 新的学习状态
     */
    private void courseProgress(Boolean aFalse, ProgressRequest request, String oldLearnedStatus,
                                String newLearnedStatus) {
        //限制进入条件:
        // 当是第一次进入的时候，前面的百分百是false，导致全部为false。
        //当第二次进入的时候，前面的百分之百为true。后面的就必须为false，才能跳过执行业务代码。
        log.info(oldLearnedStatus);
        log.info(newLearnedStatus);
        boolean flag = false;
        if (oldLearnedStatus != null) {
            flag = !aFalse && (newLearnedStatus.equals("LEARNING") || oldLearnedStatus.equals("COMPALETE")
                    && newLearnedStatus.equals("COMPALETE"));
        }

        //当为false的时候跳过，不执行return
        if (flag) {
            return;
        }
        QueryWrapper<stuCouProgress> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cou_id",request.getCouId());
        queryWrapper.eq("stu_id",request.getStuId());
        stuCouProgress stuCouProgress=stuCouProgressMapper.selectOne(queryWrapper);
        if (stuCouProgress!=null){
            stuCouProgress updateProgress=new stuCouProgress();
            updateProgress.setId(stuCouProgress.getId());
            updateProgress.setLastChaId(request.getChaId());
            //填充学习状态和学习完的节数量
            populateCourseStatus(updateProgress, stuCouProgress.getLearnedSectionCount(), newLearnedStatus,request);
            stuCouProgressMapper.updateById(updateProgress);
        }
        else {
            stuCouProgress stuCouProgressInsert=new stuCouProgress();
            stuCouProgressInsert.setCouId(request.getCouId());
            stuCouProgressInsert.setStuId(request.getStuId());
            stuCouProgressInsert.setLastChaId(request.getChaId());
            //填充学习状态和学习完的节数量
            populateCourseStatus(stuCouProgressInsert, 0L, newLearnedStatus,request);
            stuCouProgressMapper.insert(stuCouProgressInsert);
        }


    }

    /**
     * @param updateUserCourseInsert 参数
     * @param count                  旧的已学完节
     * @param newLearnedStatus       新的学习状态
     */
    private void populateCourseStatus(stuCouProgress updateUserCourseInsert, Long count, String newLearnedStatus,ProgressRequest request) {
        //已学完
        if (newLearnedStatus.equals("COMPALETE")) {
            QueryWrapper<learningProgress> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("cou_id",request.getCouId());
            queryWrapper.eq("stu_id",request.getStuId());
            queryWrapper.eq("learned_status","COMPALETE");
            Long truecount= progressServiceimpl.count(queryWrapper);
            Long sectionLearnedCount = getSectionLearnedCount(request);
            updateUserCourseInsert.setLearnedSectionCount(truecount);
            Long times=progressMapper.selectList(queryWrapper).stream().mapToLong(learningProgress::getMediaProgress).sum();
            updateUserCourseInsert.setTime(times);
            if(truecount.equals(sectionLearnedCount)){
                updateUserCourseInsert.setLearnedStatus("COMPALETE");
            }else{
                updateUserCourseInsert.setLearnedStatus("LEARNING");
            }
            return;
        }
        //未学完
        updateUserCourseInsert.setLearnedSectionCount(count);
        updateUserCourseInsert.setLearnedStatus("LEARNING");
    }


    private Long getSectionLearnedCount(ProgressRequest request) {
        QueryWrapper<chapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",request.getCouId());
        return chapterMapper.selectCount(queryWrapper);
    }



    public IPage<StuProgressDTO>
    findClassProgress(Integer pageNum,Integer pageSize,String stuId,String classId,String couId,
                 String deleted,String couType,String couChileType,String learnedStatus,String couName){
        QueryWrapper<StuProgressDTO> wrapper  = new QueryWrapper<>();
        Page<StuProgressDTO> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(stuId)) {
            wrapper.eq("scp.stu_id",stuId);
        }

        if (!"".equals(couId)) {
            wrapper.eq("c.cou_id",couId);
        }

        if (!"".equals(classId)) {
            wrapper.eq("sc.class_id",classId);
        }


        if (!"".equals(deleted)) {
            wrapper.eq("c.deleted",deleted);
        }
        if (!"".equals(couName)) {
            wrapper.eq("c.cou_name",couName);
        }

        if (!"".equals(couType)) {
            wrapper.eq("c.cou_type",couType);
            if (!"".equals(couChileType)) {
                wrapper.eq("c.cou_chile_type",couChileType);
            }

        }

        if (!"".equals(learnedStatus)) {
            wrapper.eq("scp.learned_status",learnedStatus);
        }

        return progressMapper.findClassProgress(page, wrapper);
    }

    public IPage<StuProgressDTO>
    findProgress(Integer pageNum,Integer pageSize,String stuId,String couId,
                 String deleted,String couType,String couChileType,String learnedStatus,String couName){
        QueryWrapper<StuProgressDTO> wrapper  = new QueryWrapper<>();
        Page<StuProgressDTO> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(stuId)) {
            wrapper.eq("scp.stu_id",stuId);
        }

        if (!"".equals(couId)) {
            wrapper.eq("c.cou_id",couId);
        }

        if (!"".equals(deleted)) {
            wrapper.eq("c.deleted",deleted);
        }
        if (!"".equals(couName)) {
            wrapper.eq("c.cou_name",couName);
        }

        if (!"".equals(couType)) {
            wrapper.eq("c.cou_type",couType);
            if (!"".equals(couChileType)) {
                wrapper.eq("c.cou_chile_type",couChileType);
            }

        }

        if (!"".equals(learnedStatus)) {
            wrapper.eq("scp.learned_status",learnedStatus);
        }

        return progressMapper.findProgress(page, wrapper);
    }


}
