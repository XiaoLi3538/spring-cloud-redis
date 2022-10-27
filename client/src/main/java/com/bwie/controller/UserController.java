package com.bwie.controller;

import com.bwie.req.UserReq;
import com.bwie.res.UserRes;
import com.bwie.service.UserService;
import com.bwie.utils.ImageVerificationCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author 小李-Code
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 手机号登录页面
     *
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        return "phone_login";
    }

    /**
     * 发送手机验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("/code")
    @ResponseBody
    public Integer sendCode(@RequestParam("phone") String phone) {
        return userService.sendCode(phone);
    }

    /**
     * 登录功能
     *
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public UserRes login(@RequestBody UserReq userReq) {
        return userService.login(userReq);
    }

    /**
     * 获取图片验证码
     *
     * @param response
     */
    @GetMapping("/getImageCode")
    public void getImageCode(HttpServletResponse response) {
        // 1.获取图片验证码工具类
        ImageVerificationCode imageVerificationCode = new ImageVerificationCode();
        // 2.获取图片
        BufferedImage image = imageVerificationCode.getImage();
        // 3.获取图片文本
        String text = imageVerificationCode.getText();
        // 4.将图片验证码存入redis
        redisTemplate.opsForValue().set("user:login:imageCode:", text, 1, TimeUnit.MINUTES);
        // 5.返回图片验证码
        try {
            ImageVerificationCode.output(image, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
