package com.bwie.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小李-Code
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 短信验证码
     */
    private String code;
    /**
     * 图片验证码
     */
    private String imageCode;

}
