package com.register.server.edu;

import java.util.LinkedList;

/**
 * 增量注册表
 */
public class DeltaRegistry {

    private LinkedList<ServiceRegistry.RecentlyChangedServiceInstance> recentlyChangedQueue;
    private Long serviceInstanceTotalCount;

    public DeltaRegistry(LinkedList<ServiceRegistry.RecentlyChangedServiceInstance> recentlyChangedQueue,
                         Long serviceInstanceTotalCount) {
        this.recentlyChangedQueue = recentlyChangedQueue;
        this.serviceInstanceTotalCount = serviceInstanceTotalCount;
    }

    public LinkedList<ServiceRegistry.RecentlyChangedServiceInstance> getRecentlyChangedQueue(){
        return recentlyChangedQueue;
    }

    public void setRecentlyChangedQueue(LinkedList<ServiceRegistry.RecentlyChangedServiceInstance>
                                                recentlyChangedQueue){
        this.recentlyChangedQueue = recentlyChangedQueue;
    }

    public Long getServiceInstanceTotalCount() {
        return serviceInstanceTotalCount;
    }

    public void setServiceInstanceTotalCount(Long serviceInstanceTotalCount) {
        this.serviceInstanceTotalCount = serviceInstanceTotalCount;
    }

}
