package cc.fun.hutoolemail.demos.web.Util;

import cn.hutool.jwt.JWTUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Jwt {
    public static String createToken(String username) {
        StringBuilder  s = new StringBuilder();
        byte[] b = username.getBytes();
        for(byte value : b){
            s.append(value);
        }
        String s1 = s.toString();
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("uid", s1);
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };
        return JWTUtil.createToken(map, "KURIYAMAMIRAII".getBytes());
    }
}
