package com.bwie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.bwie.feign.UserFeign;
import com.bwie.req.UserReq;
import com.bwie.res.UserRes;
import com.bwie.service.UserService;
import com.bwie.utils.JwtUtil;
import com.bwie.utils.RegexUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 小李-Code
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserFeign userFeign;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 发送手机验证码
     *
     * @param phone
     * @return
     */
    @Override
    public Integer sendCode(String phone) {
        // 1.校验手机号
        if (!RegexUtils.isMobileExact(phone)) {
            // 2.如果不符合，返回错误信息
            return 0;
        }
        // 3.符合，生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 4.保存验证码到redis
        redisTemplate.opsForValue().set("user:login:code:" + phone, code, 1, TimeUnit.MINUTES);
        // 5.发送验证码
        System.out.println("短信验证码:" + code);
        return 1;
    }

    /**
     * 登录功能
     *
     * @param userReq
     * @return
     */
    @Override
    public UserRes login(UserReq userReq) {
        // 1.校验手机号
        String phone = userReq.getPhone();
        if (!RegexUtils.isMobileExact(phone)) {
            // 2.如何不符合，返回错误信息
            return null;
        }
        // 3.从redis获取短信验证码并校验
        String cacheCode = (String) redisTemplate.opsForValue().get("user:login:code:" + phone);
        String code = userReq.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            return null;
        }
        // 4.从redis获取图片验证按并校验
        String cacheImageCode = (String) redisTemplate.opsForValue().get("user:login:imageCode:");
        String imageCode = userReq.getImageCode();
        if (cacheImageCode == null || !cacheImageCode.equals(imageCode)) {
            // 不一致，报错
            return null;
        }
        // 5.一致，根据手机号查询用户
        UserRes userRes = userFeign.login(userReq);
        // 6.判断用户是否存在
        if (userRes == null) {
            return null;
        }
        // 7.保存用户信息到redis中
        // 7.1.随机生成token，作为登录令牌
        String jsonString = JSON.toJSONString(userRes);
        String token = jwtUtil.createJWT(userRes.getUserId() + "", jsonString, "1");
        userRes.setToken(token);
        // 7.2.将user对象转为Hash存储
        UserRes userRes1 = BeanUtil.copyProperties(userRes, UserRes.class);
        Map<String, Object> userResMap = BeanUtil.beanToMap(userRes1);
        // 7.3.存储
        redisTemplate.opsForHash().putAll("user:login:user:", userResMap);
        redisTemplate.expire("user:login:user:", 1, TimeUnit.HOURS);
        redisTemplate.opsForValue().increment("loginCount",1);
        redisTemplate.expire("loginCount",24,TimeUnit.HOURS);
        Integer loginCount = (Integer) redisTemplate.opsForValue().get("loginCount");
        if (loginCount > 5) {
            return null;
        }
        // 8.返回token
        return userRes;
    }
}
