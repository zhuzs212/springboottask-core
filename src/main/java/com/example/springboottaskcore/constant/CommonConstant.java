package com.example.springboottaskcore.constant;

import org.springframework.stereotype.Component;

@Component
public class CommonConstant {

    public static final String AUTH_TOKEN_KEY = "Authorization";

    public static final String REDIS_USER_INFO = "userInfo";
    public static final String REDIS_USER_ACTIONS = "userActions";
    public static final String REDIS_CREATE_TIME = "createTime";
    public static final Integer REDIS_LOGIN_EXPIRE = 900; // 时间加长
    public static final Integer REDIS_SMS_EXPIRE = 900; // 时间加长
}
