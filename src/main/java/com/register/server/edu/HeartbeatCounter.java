package com.register.server.edu;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 心跳测量计数器
 */
public class HeartbeatCounter {

    /**
     * 单例实例
     */
    private static HeartbeatCounter instance = new HeartbeatCounter();

    private HeartbeatCounter(){}

    /**
     * 最近一分钟的心跳次数
     */
    private AtomicLong latestMinuteHeartbeatRate = new AtomicLong(0L);

    /**
     * 最近一分钟的时间戳
     */
    private long latestMinuteTimestamp = System.currentTimeMillis();

    /**
     * 获取单例实例
     * @return
     */
    public static HeartbeatCounter getInstance() {
        return instance;
    }

    /**
     * 增加一次最近一分钟的心跳次数
     */
    public /*synchronized*/void increment(){
        latestMinuteHeartbeatRate.incrementAndGet();
    }

    /**
     * 获取最近一分钟的心跳次数
     * @return
     */
    public /*synchronized*/ long get(){
        return latestMinuteHeartbeatRate.get();
    }

    private class Daemon extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - latestMinuteTimestamp > 60 * 1000) {
                        latestMinuteHeartbeatRate =  new AtomicLong(0L);
                        latestMinuteTimestamp = System.currentTimeMillis();
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
