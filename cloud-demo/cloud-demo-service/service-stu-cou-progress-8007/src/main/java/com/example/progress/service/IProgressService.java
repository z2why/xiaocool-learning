package com.example.progress.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.progress.controller.dto.ProgressRequest;
import entity.comment;
import entity.learningProgress;

public interface IProgressService extends IService<learningProgress> {
    void progress(ProgressRequest request);
}
