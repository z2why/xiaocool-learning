package com.example.comment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.comment.mapper.CommentMapper;
import entity.comment;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

public interface ICommentService  extends IService<comment> {


}
