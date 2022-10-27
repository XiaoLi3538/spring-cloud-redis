package com.bwie.service;

import com.bwie.req.UserReq;
import com.bwie.res.UserRes;

/**
 * @author 小李-Code
 */
public interface UserService {

    /**
     * 发送手机验证码
     *
     * @param phone
     * @return
     */
    Integer sendCode(String phone);

    /**
     * 登录功能
     *
     * @param userReq
     * @return
     */
    UserRes login(UserReq userReq);
}
