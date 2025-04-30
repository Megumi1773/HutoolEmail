package cc.fun.hutoolemail.demos.web.Service;

import cc.fun.hutoolemail.demos.web.DTO.LoginUser;
import cc.fun.hutoolemail.demos.web.Entity.User;
import cc.fun.hutoolemail.demos.web.Mapper.UserMapper;
import cc.fun.hutoolemail.demos.web.Misc.Result;
import cc.fun.hutoolemail.demos.web.Util.Jwt;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Result<Object> login(LoginUser u) {
        int result = userMapper.Login(u);
        if (result == 0) {
            return Result.error("用户名或密码错误");
        }
        if (result == -1) {
            return Result.error("用户不存在");
        }

        @Getter
        class Lm{
            private final String username;
            private final String token;
            public Lm(String username, String token) {
                this.username = username;
                this.token = token;
            }
        }
        return Result.success("登入成功", new Lm(u.getUsername(), Jwt.createToken(u.getUsername())));
    }
}
