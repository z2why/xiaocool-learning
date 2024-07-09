package com.example.chapter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.chapter.mapper.CouChaAppraiseMapper;
import com.example.chapter.service.ICouChaAppraiseService;
import entity.StuChaCouAppDTO;
import entity.StuCouAppDTO;
import entity.courseChapterAppraise;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CouChaAppraiseServiceimpl extends ServiceImpl<CouChaAppraiseMapper, courseChapterAppraise> implements ICouChaAppraiseService {
    @Resource
    private CouChaAppraiseMapper couChaAppraiseMapper;


    public List<StuChaCouAppDTO>
    findStuCouChaApp(String stuId, String couId,String chaId, String chaAppraise){
        QueryWrapper<StuChaCouAppDTO> queryWarpper  = new QueryWrapper<>();
        if (!"".equals(couId)){
            queryWarpper.eq("cca.cou_id",couId);
        }

        if (!"".equals(stuId)){
            queryWarpper.eq("cca.stu_id",stuId);
        }

        if (!"".equals(chaId)){
            queryWarpper.eq("cca.cha_id",chaId);
        }

        if (!"".equals(chaAppraise)){
            queryWarpper.like("cca.cou_appraise",chaAppraise);
        }
        return couChaAppraiseMapper.findStuCouChaApp(queryWarpper);
    }

}
