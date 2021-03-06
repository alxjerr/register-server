package com.register.server.edu;

/**
 * 注册响应
 */
public class RegisterResponse {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    /**
     * 注册响应状态：SUCCESS、FAILURE
     */
    private String status;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
