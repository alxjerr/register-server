package com.register.server.edu;

import java.util.HashMap;
import java.util.Map;

/**
 * 完整的服务实例的信息
 */
public class Applications {

    private Map<String, Map<String,ServiceInstance>> registry = new HashMap<>();

    public Applications() {
    }

    public Applications(Map<String, Map<String, ServiceInstance>> registry) {
        this.registry = registry;
    }

    public  Map<String, Map<String,ServiceInstance>> getRegistry(){
        return registry;
    }

    public void set(Map<String, Map<String,ServiceInstance>> registry){
        this.registry = registry;
    }
}
