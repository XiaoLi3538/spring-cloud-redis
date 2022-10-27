package com.bwie.config;

import cn.hutool.core.bean.BeanUtil;
import com.bwie.res.UserRes;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 拦截器
 * @author 小李-Code
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 前置拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取redis中的用户
        Map<Object, Object> map = redisTemplate.opsForHash().entries("user:login:user:");
        UserRes userRes = BeanUtil.fillBeanWithMap(map, new UserRes(), true);
        System.out.println("HashMap转成对象后:" + userRes);
        // 2.判断用户是否存在
        if (userRes == null) {
            // 3.不存在，拦截 重定向页面
            response.sendRedirect("/user/toLogin");
            return false;
        }
        // 4.存在，保存用户信息到ThreadLocal
        new ThreadLocal<UserRes>().set(userRes);
        // 5.放行
        return true;
    }

    /**
     * 试图渲染拦截
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        new ThreadLocal<UserRes>().remove();
    }
}
