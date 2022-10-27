package com.bwie.service;

import com.bwie.req.UserReq;
import com.bwie.res.UserRes;

/**
 * @author 小李-Code
 */
public interface UserService {

    /**
     * 登录功能
     *
     * @param userReq
     * @return
     */
    UserRes login(UserReq userReq);
}
