package com.example.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.comment.mapper.topicQuestionMapper;
import com.example.comment.service.ItopicQuestionService;
import entity.comment;
import entity.cousType;
import entity.topicQuestion;
import entity.tqUserDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class topicQuestionServiceimpl extends ServiceImpl<topicQuestionMapper, topicQuestion> implements ItopicQuestionService {

    @Resource
    private topicQuestionMapper topicQuestionMapper;


    public IPage<tqUserDTO>
    tqUserPage(int pageNum, int pageSize,
                String tqId, String uId,String stuId,
                String content, String createTime,
                String isQuestion, String answeredTqId,
                String truename, String isSelect
    ){
        QueryWrapper<tqUserDTO> wrapper  = new QueryWrapper<>();
        Page<tqUserDTO> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(tqId)){
            wrapper.eq("c.tq_id",tqId);
        }

        if (!"".equals(uId)){
            wrapper.eq("u.id",uId);
        }

        if (!"".equals(stuId)){
            wrapper.eq("u.stu_id",stuId);
        }

        if (!"".equals(content)){
            wrapper.like("c.content",content);

        }

        if (!"".equals(createTime)){
            wrapper.eq("c.create_time",createTime);
        }

        if (!"".equals(isQuestion)){
            wrapper.eq("c.is_question",isQuestion);

        }

        if (!"".equals(truename)){
            wrapper.eq("c.truename",truename);

        }
        if (!"".equals(answeredTqId)){
            wrapper.eq("c.answered_tq_id",answeredTqId);
        }

        if (!"".equals(isSelect)){
            wrapper.eq("c.is_select",isSelect);

        }
        wrapper.select().orderByDesc("c.create_time");

        IPage<tqUserDTO> tqUserPage = topicQuestionMapper.tqUserPage(page, wrapper);
        return tqUserPage;
    }




}
