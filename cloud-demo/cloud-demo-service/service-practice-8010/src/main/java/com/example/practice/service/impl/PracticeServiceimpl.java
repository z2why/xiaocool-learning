package com.example.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.practice.mapper.PracticeMapper;
import com.example.practice.service.IPracticeService;
import entity.practice;
import entity.stuCou;
import entity.stuPractice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PracticeServiceimpl extends ServiceImpl<PracticeMapper, practice> implements IPracticeService {
    @Resource
    private PracticeMapper practiceMapper;

    public IPage<stuPractice>
    PracticePage(int pageNum, int pageSize, String praId,
                       String praName, String praSite, String teaId,
                       String couId, String couName, String praTime,
                       String deleted ){
        QueryWrapper<stuPractice> wrapper  = new QueryWrapper<>();
        Page<stuPractice> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(praId)){
            wrapper.eq("p.pra_id",praId);
        }
        if (!"".equals(praName)){
            wrapper.like("p.pra_name",praName);
        }
        if (!"".equals(praSite)){
            wrapper.like("p.pra_site",praSite);
        }
        if (!"".equals(couId)){
            wrapper.eq("c.cou_id",couId);
        }
        if (!"".equals(couName)){
            wrapper.like("c.cou_name",couName);
        }
        if (!"".equals(teaId)){
            wrapper.eq("p.tea_id",teaId);
        }
        if (!"".equals(praTime)){
            wrapper.eq("p.pra_time",praTime);
        }

        if (!"".equals(deleted)){
            wrapper.eq("p.deleted",deleted);
        }
        IPage<stuPractice> studentPage = practiceMapper.PracticePage(page, wrapper);
        return studentPage;
    }

    public IPage<stuPractice>
    selectPracticePage(int pageNum, int pageSize, String praId,
                       String praName, String praSite, String teaId,
                       String couId, String couName, String praTime,
                       String stuId, String stuName,String audit,String deleted ){
        QueryWrapper<stuPractice> wrapper  = new QueryWrapper<>();
        Page<stuPractice> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(praId)){
            wrapper.eq("p.pra_id",praId);
        }
        if (!"".equals(praName)){
            wrapper.like("p.pra_name",praName);
        }
        if (!"".equals(praSite)){
            wrapper.like("p.pra_site",praSite);
        }
        if (!"".equals(couId)){
            wrapper.eq("c.cou_id",couId);
        }
        if (!"".equals(couName)){
            wrapper.like("c.cou_name",couName);
        }
        if (!"".equals(stuId)){
            wrapper.eq("s.stu_id",stuId);
        }
        if (!"".equals(teaId)){
            wrapper.eq("t.tea_id",teaId);
        }
        if (!"".equals(praTime)){
            wrapper.eq("p.pra_time",praTime);
        }
        if (!"".equals(stuName)){
            wrapper.like("s.stu_name",stuName);
        }
        if (!"".equals(audit)){
            wrapper.like("sp.audit",audit);
        }
        if (!"".equals(deleted)){
            wrapper.eq("p.deleted",deleted);
        }
        IPage<stuPractice> studentPage = practiceMapper.selectPracticePage(page, wrapper);
        return studentPage;
    }


}
