package com.register.server.edu;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 心跳测量计数器
 */
@SuppressWarnings("unused")
public class HeartbeatCounter {

    /**
     * 单例实例
     */
    private static HeartbeatCounter instance = new HeartbeatCounter();

    private HeartbeatCounter(){
        Daemon daemon = new Daemon();
        daemon.setDaemon(true);
        daemon.start();
    }

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
                        while (true) {
                            Long expectedValue = latestMinuteHeartbeatRate.get();
                            if (latestMinuteHeartbeatRate.compareAndSet(expectedValue,0L)) {
                                break;
                            }
                        }

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
