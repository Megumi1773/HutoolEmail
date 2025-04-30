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
        // 先查询用户是否存在
        User user = userMapper.findByUsername(u.getUsername());
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 再验证密码
        User loginUser = userMapper.login(u);
        if (loginUser == null) {
            return Result.error("密码错误");
        }

        @Getter
        class Lm {
            private final String username;
            private final String token;
            public Lm(String username, String token) {
                this.username = username;
                this.token = token;
            }
        }
        return Result.success("登录成功", new Lm(u.getUsername(), Jwt.createToken(u.getUsername())));
    }
}
