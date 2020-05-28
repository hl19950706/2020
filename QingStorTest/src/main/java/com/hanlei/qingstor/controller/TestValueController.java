package com.hanlei.qingstor.controller;

import com.hanlei.qingstor.utils.RecordFailConfig;
import com.hanlei.qingstor.utils.S3Template;
import com.hanlei.qingstor.utils.ThreadPoolFactory;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @ClassName TestValueController
 * @Description
 * @Author hanlei
 * @Date 2020/5/26 4:52 下午
 * @Version 1.0
 */
@Controller
@RequestMapping("qstest")
public class TestValueController {

    private static final Logger logger = LoggerFactory.getLogger(TestValueController.class);

    @Autowired
    private S3Template s3Template;

    @RequestMapping("up")
    public void uptest() {
        Queue<List<String>> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 100; i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                String s = i + "-" + j;
                list.add(s);
                File file = new File(System.getProperty("user.home") + "/file/" + s + ".txt");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        logger.error("创建文件失败:{}", s);
                    }
                }
            }
            queue.add(list);
        }
        logger.info("队列size:{}", queue.size());
        for (int i = 0; i < 5; i++) {
            ThreadPoolFactory.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String threadname = Thread.currentThread().getName();
                    logger.info("当前线程名称:{}已启动，开始去读队列任务信息", threadname);
                    while (!queue.isEmpty()) {
                        List<String> task = null;
                        try {
                            if (queue.isEmpty()) {
                                Long endTime = System.currentTimeMillis();
                                logger.info("当前线程名称:{},当前队列任务已分配完成,当前时间{}，等候线程执行完成", threadname, DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss"));
                            } else {
                                task = queue.poll();
                                logger.info("当前线程名称:{},读取队列任务信息为:{}", threadname, task);
                            }
                        } catch (Exception e) {
                            logger.info("当前线程名称:{},读取队列任务信息出错:{}", threadname, e);
                        }
                        for (String s : task) {
                            try {
                                String keyName = s;
                                File file = new File(System.getProperty("user.home") + "/file/" + s + ".txt");
                                Map<String, Object> objectMap = s3Template.uploadFile(file, keyName, null);
                                logger.info("当前线程名称{},上传成功：任务ID{},上传key：{},上传地址:{},上传id:{}", threadname, task, keyName, objectMap.get(S3Template.PATH), s);
                            } catch (Exception e) {
                                logger.info("当前线程名称{},上传失败：任务ID{}，文件id:{}, 原因:{}", threadname, task, s, RecordFailConfig.RECORD_QY_UP_ERROR_DESC, e);
                                continue;
                            }
                            try {
                                //测试下载
                                s3Template.downFile(s, System.getProperty("user.home") + "/downfile/" + s + "down.txt");
                                logger.info("当前线程名称{},任务ID{},测试下载文件成功:{}", threadname, task, s);
                            } catch (Exception e) {
                                logger.error("当前线程名称{},任务ID{},测试下载文件失败:{}", threadname, task, s, e);
                            }
                        }
                    }
                }
            });
        }
    }
}
