package com.bwie.feign;

import com.bwie.req.UserReq;
import com.bwie.res.UserRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 小李-Code
 */
@FeignClient("SERVER/user")
public interface UserFeign {

    /**
     * 登录功能
     *
     * @param userReq
     * @return
     */
    @PostMapping("/login")
    public UserRes login(@RequestBody UserReq userReq);
}
