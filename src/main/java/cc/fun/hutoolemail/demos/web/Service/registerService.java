package cc.fun.hutoolemail.demos.web.Service;

import cc.fun.hutoolemail.demos.web.DTO.GetCodeEmail;
import cc.fun.hutoolemail.demos.web.DTO.RegUser;
import cc.fun.hutoolemail.demos.web.Entity.vEmail;
import cc.fun.hutoolemail.demos.web.Mapper.RegisterMapper;
import cc.fun.hutoolemail.demos.web.Mapper.UserMapper;
import cc.fun.hutoolemail.demos.web.Misc.Result;
import cn.hutool.core.date.DateTime;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class registerService {
    @Autowired
    private RegisterMapper registerMapper;
    @Autowired
    private UserMapper userMapper;

    public Result<String> sendMail(GetCodeEmail email) {
        Random r = new Random();
        int code = r.nextInt(899999) + 100000;
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com");
        account.setPort(465);
        account.setAuth(true);
        account.setFrom("479156530@qq.com");
        account.setUser("479156530");
        account.setPass("wasvwjlkdknqbijf");
        account.setSslEnable(true);
        String content = "<h1>您的验证码为：" + code + "</h1>";
        MailUtil.send(account, email.getEmail(), "注册SevenMusic", content, false);
        //现在时间
        DateTime now = DateTime.now();
        //保存验证码
        int c = registerMapper.SaveEmailCode(new vEmail(null, email.getEmail(), code, now));
        if (c == 0) {
            return Result.error("发送失败");
        }

        return Result.success("发送成功");
    }

    public Result<String> RegAccount(RegUser ru) {
        vEmail e = registerMapper.GetEmailCode(ru);
        if (!Objects.equals(e.getCode(), ru.getCode())) {
            return Result.error("验证码错误");
        }
        int c = userMapper.Register(ru.getEmail(), ru.getPassword());
        return Result.success("注册成功");
    }
}
