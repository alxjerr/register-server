package com.register.server.edu;

import java.util.Map;

/**
 * 微服务存活状态的监控组件
 */
public class ServiceAliveMonitor {

    /**
     * 检查服务实例是否存活的间隔
     */
    private static final Long CHECK_ALIVE_INTERVAL = 60 * 1000L;

    private Daemon daemon;

    public ServiceAliveMonitor() {
        this.daemon = new Daemon();
        // 设置一个标志位，代表这个线程是一个daemon线程 -> 后台线程
        daemon.setDaemon(true);
    }

    /**
     * 启动后台线程
     */
    public void start(){
        daemon.start();
    }

    /**
     * 负责监控微服务存活状态的后台线程
     */
    private class Daemon extends Thread{
        private Registry registry = Registry.getInstance();

        @Override
        public void run() {
            Map<String,Map<String,ServiceInstance>> registryMap = null;
            while (true) {
                try {
                    registryMap = registry.getRegistry();
                    for (String serviceName : registryMap.keySet()) {
                        Map<String,ServiceInstance> serviceInstanceMap = registryMap.get(serviceName);

                        for (ServiceInstance serviceInstance : serviceInstanceMap.values()) {
                            // 说明服务实例距离上一次发送心跳已经超过90秒了
                            // 认为这个服务就死了
                            // 从注册表中摘除这个服务实例
                            if (!serviceInstance.isAlive()) {
                                registry.remove(serviceName,serviceInstance.getServiceInstanceId());
                            }
                        }
                    }

                    Thread.sleep(CHECK_ALIVE_INTERVAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
