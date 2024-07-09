package com.example.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.comment.mapper.CommentMapper;
import com.example.comment.service.ICommentService;
import entity.comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentServiceimpl extends ServiceImpl<CommentMapper, comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;



    public IPage<comment>
    commentPage(int pageNum, int pageSize,String cId,
                String role,String infoId){
        QueryWrapper<comment> wrapper  = new QueryWrapper<>();
        Page<comment> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(cId)){
            wrapper.eq("c.cou_id",cId);
        }
        if (!"".equals(infoId)){
            wrapper.eq("c.info_id",infoId);
        }
        if (!"".equals(role)){
            wrapper.eq("c.role",role);
        }
//        wrapper.select().orderByDesc("c.c_date");
        IPage<comment> commentPage = commentMapper.commentPage(page, wrapper);
        return commentPage;
    }



}
