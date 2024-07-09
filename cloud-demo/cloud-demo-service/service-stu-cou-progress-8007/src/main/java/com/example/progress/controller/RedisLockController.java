package com.example.progress.controller;

import cn.hutool.log.Log;
import com.example.progress.Exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.Constants;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Slf4j
@RestController
public class RedisLockController {
    @Resource
    private RedissonClient redissonClient;
    private static int a = 50;
    private static final Log LOG = Log.get();

    @RequestMapping("/redis_test")
    public String redisTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    RLock rLock = redissonClient.getLock("zhangsan");
                    //rLock.lock();
                    log.info("获取锁成功！");
                    try {
                        //业务逻辑，存储入DB
                        //加锁，最多等待10s，10s之后自动释放锁
                        if (rLock.tryLock(10, 10, TimeUnit.SECONDS)) {
                            Thread.sleep(50);
                            if (a > 0) {
                                a--;
                            }
                        }
                    } catch (Exception e) {
                        LOG.error(e);
                        throw new ServiceException(Constants.CODE_500, "系统错误");
                    } finally {
                        //释放锁
                        if (rLock.isLocked()) {
                            rLock.unlock();
                        }
                    }
                    log.info("最后剩余：" + a);
                }
            });
        }
        executorService.shutdown();

        return "success";
    }
}