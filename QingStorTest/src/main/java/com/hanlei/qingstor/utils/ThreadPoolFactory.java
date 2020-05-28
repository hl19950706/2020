package com.hanlei.qingstor.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPoolFactory
 * @Description
 * @Author hanlei
 * @Date 2020/5/9 9:52 上午
 * @Version 1.0
 */
public class ThreadPoolFactory {

    private static final ThreadFactory threadFactory =
            new ThreadFactoryBuilder().setNameFormat("QC_UP_Thread_%s").build();

    public static ExecutorService executorService = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)
            , threadFactory, new ThreadPoolExecutor.AbortPolicy());
}
