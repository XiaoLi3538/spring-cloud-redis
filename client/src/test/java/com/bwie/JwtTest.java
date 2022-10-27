package com.bwie;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.bwie.pojo.User;
import com.bwie.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小李-Code
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void jwtTest01() {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, 10);

        Map<String, Object> payload = new HashMap<String, Object>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put("userName", "zhangsan");
        payload.put("passWord", "666889");

        String key = "aabb";
        String token = JWTUtil.createToken(payload, key.getBytes());
        System.out.println("hutool工具类jwt:" + token);
    }

    @Test
    public void jwtTest02() {
        User user = new User(1, "小李", "123", "13513860573", "123");
        String s = JSON.toJSONString(user);
        String jwt = jwtUtil.createJWT(user.getUserId() + "", s, "1");
        System.out.println("工具类jwt:" + jwt);
    }
    //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzV29yZCI6IjY2Njg4OSIsIm5iZiI6MTY2NjY4MTE4MCwiZXhwIjoxNjY2NjgxNzgwLCJ1c2VyTmFtZSI6InpoYW5nc2FuIiwiaWF0IjoxNjY2NjgxMTgwfQ.0yCBwyUOaCEw4Lqt-Moy9B6CDwQQ8BMkXj1jWCEMtNc
    //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoie1wiY29kZVwiOlwiMTIzXCIsXCJwYXNzd29yZFwiOlwiMTIzXCIsXCJwaG9uZVwiOlwiMTM1MTM4NjA1NzNcIixcInVzZXJJZFwiOjEsXCJ1c2VyTmFtZVwiOlwi5bCP5p2OXCJ9IiwiaWF0IjoxNjY2NjgxMTM2LCJyb2xlcyI6IjEiLCJleHAiOjE2NjY2ODE0OTZ9.4iCnQZnLdSNw0Jz09Ex1hwNcNY_RLUL49uSgXSWA2Qw

    @Test
    public void jwtTest03() {
        String key = "aabb";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzV29yZCI6IjY2Njg4OSIsIm5iZiI6MTYzNTE1MDI3NiwiZXhwIjoxNjM1MTUwODc2LCJ1c2VyTmFtZSI6InpoYW5nc2FuIiwiaWF0IjoxNjM1MTUwMjc2fQ.Cq2AHyrZ-Q7U7O5BBPdEIBrm7aDtjQh4ZDvtIcLzQvg";
        JWT jwt = JWTUtil.parseToken(token);

        boolean verifyKey = jwt.setKey(key.getBytes()).verify();
        System.out.println(verifyKey);

        boolean verifyTime = jwt.validate(0);
        System.out.println(verifyTime);
    }
}
