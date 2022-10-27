package com.bwie.service.impl;

import com.bwie.mapper.UserMapper;
import com.bwie.req.UserReq;
import com.bwie.res.UserRes;
import com.bwie.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 小李-Code
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 登录功能
     *
     * @param userReq
     * @return
     */
    @Override
    public UserRes login(UserReq userReq) {
        return userMapper.login(userReq);
    }
}
