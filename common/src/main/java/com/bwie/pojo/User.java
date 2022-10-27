package com.bwie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小李-Code
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private String code;

}
