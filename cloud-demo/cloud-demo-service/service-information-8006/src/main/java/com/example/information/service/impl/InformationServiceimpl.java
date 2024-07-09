package com.example.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.information.mapper.InformationMapper;
import com.example.information.service.IinformationService;
import entity.infoUserDTO;
import entity.information;
import entity.tqUserDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class InformationServiceimpl extends ServiceImpl<InformationMapper, information> implements IinformationService {

@Resource
private InformationMapper informationMapper;


    public IPage<infoUserDTO>
    infoUserPage(int pageNum, int pageSize,
                 String infoId, String infoTitle,
                 String infoAuthor, String infoData, String infoLike

    ){
        QueryWrapper<infoUserDTO> queryWarpper  = new QueryWrapper<>();
        Page<infoUserDTO> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(infoId)){
            queryWarpper.eq("c.info_id",infoId);
        }
        if (!"".equals(infoTitle)){
            queryWarpper.like("c.info_title",infoTitle);
        }
        if (!"".equals(infoAuthor)){
            queryWarpper.like("c.info_author",infoAuthor);
        }

        queryWarpper.select().orderByDesc("c.info_data");

        IPage<infoUserDTO> infoUserPage = informationMapper.infoUserPage(page, queryWarpper);
        return infoUserPage;
    }
}
