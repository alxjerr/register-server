package com.register.server.edu;

/**
 * 心跳测量计数器
 */
public class HeartbeatMessuredRate {

    /**
     * 单例实例
     */
    private static HeartbeatMessuredRate instance = new HeartbeatMessuredRate();

    private HeartbeatMessuredRate(){}

    /**
     * 最近一分钟的心跳次数
     */
    private long latestMinuteHeartbeatRate = 0L;

    /**
     * 最近一分钟的时间戳
     */
    private long latestMinuteTimestamp = System.currentTimeMillis();

    /**
     * 获取单例实例
     * @return
     */
    public static HeartbeatMessuredRate getInstance() {
        return instance;
    }

    /**
     * 增加一次最近一分钟的心跳次数
     */
    public synchronized void increment(){
        latestMinuteHeartbeatRate++;
    }

    /**
     * 获取最近一分钟的心跳次数
     * @return
     */
    public synchronized long get(){
        return latestMinuteHeartbeatRate;
    }

    private class Daemon extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (HeartbeatMessuredRate.class) {
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - latestMinuteTimestamp > 60 * 1000) {
                            latestMinuteHeartbeatRate =  0L;
                            latestMinuteTimestamp = System.currentTimeMillis();
                        }
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
