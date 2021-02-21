package com.register.server.edu;

/**
 * 这个controller是负责接收register-client发送过来的请求的
 *
 */
public class RegisterServerController {

    private Registry registry = Registry.getInstance();

    /**
     * 服务注册
     * @param registerRequest 注册请求
     * @return  注册响应
     */
    public RegisterResponse register(RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            ServiceInstance serviceInstance = new ServiceInstance();
            serviceInstance.setHostname(registerRequest.getHostname());
            serviceInstance.setIp(registerRequest.getIp());
            serviceInstance.setPort(registerRequest.getPort());
            serviceInstance.setServiceInstanceId(registerRequest.getServiceInstanceId());
            serviceInstance.setServiceName(registerRequest.getServiceName());

            registry.register(serviceInstance);
            registerResponse.setStatus(RegisterResponse.SUCCESS);
        } catch (Exception e) {
            e.fillInStackTrace();
            registerResponse.setStatus(RegisterResponse.FAILURE);
        }
        return registerResponse;
    }

    /**
     * 发送心跳
     * @param heartbeatRequest  心跳请求
     * @return  心跳响应
     */
    public HeartbeatResponse heartbeat(HeartbeatRequest heartbeatRequest){
        HeartbeatResponse heartbeatResponse = new HeartbeatResponse();
        try {
            ServiceInstance serviceInstance = registry.getServiceInstance(
                    heartbeatRequest.getServiceName(),heartbeatRequest.getServiceInstanceId());
            serviceInstance.renew();
        } catch (Exception e) {
            e.printStackTrace();
            heartbeatResponse.setStatus(HeartbeatResponse.FAILURE);
        }
        return heartbeatResponse;
    }

}
