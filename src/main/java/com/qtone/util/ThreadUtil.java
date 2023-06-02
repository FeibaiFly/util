package com.qtone.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadUtil {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//默认线程池建立的线程数，当多余的线程处于空闲状态时，大于这个数字的线程会自动销毁
        taskExecutor.setCorePoolSize(10);
//最大的线程数
        taskExecutor.setMaxPoolSize(10);
//等待队列数，这里为了测试方便设置为0，实际可根据具体场景设置
        taskExecutor.setQueueCapacity(5);
        taskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
                                                     @Override
                                                     public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                                                         if (!executor.isShutdown()) {
                                                             try {
                                                                 executor.getQueue().put(r);
                                                             } catch (InterruptedException e) {
                                                                 e.printStackTrace();
                                                                 Thread.currentThread().interrupt();
                                                             }
                                                         }
                                                     }
                                                 }
        );
        taskExecutor.initialize();

        for (int i = 0; i < 100; i++) {
            final int index = i;
            taskExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println(index);

                }
            });
        }
        taskExecutor.shutdown();
        System.out.println("结束");
    }
}
