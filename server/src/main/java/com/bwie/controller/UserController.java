package com.bwie.controller;

import com.bwie.req.UserReq;
import com.bwie.res.UserRes;
import com.bwie.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 小李-Code
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录功能
     *
     * @param userReq
     * @return
     */
    @PostMapping("/login")
    public UserRes login(@RequestBody UserReq userReq) {
        return userService.login(userReq);
    }
}
