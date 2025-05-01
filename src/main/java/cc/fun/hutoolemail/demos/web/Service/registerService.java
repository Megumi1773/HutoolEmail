package cc.fun.hutoolemail.demos.web.Service;

import cc.fun.hutoolemail.demos.web.DTO.GetCodeEmail;
import cc.fun.hutoolemail.demos.web.DTO.RegUser;
import cc.fun.hutoolemail.demos.web.Entity.User;
import cc.fun.hutoolemail.demos.web.Entity.vEmail;
import cc.fun.hutoolemail.demos.web.Mapper.RegisterMapper;
import cc.fun.hutoolemail.demos.web.Mapper.UserMapper;
import cc.fun.hutoolemail.demos.web.Misc.Result;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class registerService {
    private final RegisterMapper registerMapper;
    private final UserMapper userMapper;

    @Autowired
    public registerService(RegisterMapper registerMapper, UserMapper userMapper) {
        this.registerMapper = registerMapper;
        this.userMapper = userMapper;
    }

    public Result<String> sendMail(GetCodeEmail email) {
        Random r = new Random();
        int code = r.nextInt(899999) + 100000;
        String content = "<div style=\"max-width: 600px; margin: 0 auto; padding: 20px; font-family: Arial, sans-serif; background-color: #f8f9fa; border-radius: 10px;\">" +
                "<div style=\"text-align: center; padding: 20px; background-color: #4CAF50; border-radius: 8px 8px 0 0;\">" +
                "<h1 style=\"color: white; margin: 0; font-size: 24px;\">🎉 欢迎加入 SevenMusic 🎉</h1>" +
                "</div>" +
                "<div style=\"padding: 30px; background-color: white; border-radius: 0 0 8px 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);\">" +
                "<p style=\"font-size: 16px; color: #333; line-height: 1.6;\">亲爱的用户：</p>" +
                "<p style=\"font-size: 16px; color: #333; line-height: 1.6;\">感谢您注册 SevenMusic！您的验证码是：</p>" +
                "<div style=\"background-color: #f8f9fa; padding: 15px; border-radius: 5px; text-align: center; margin: 20px 0;\">" +
                "<span style=\"font-size: 24px; font-weight: bold; color: #4CAF50; letter-spacing: 5px;\">" + code + "</span>" +
                "</div>" +
                "<p style=\"font-size: 14px; color: #666; line-height: 1.6;\">验证码有效期为5分钟，请尽快完成注册。</p>" +
                "<div style=\"margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee;\">" +
                "<p style=\"font-size: 14px; color: #666; margin: 0;\">祝您使用愉快！</p>" +
                "<p style=\"font-size: 14px; color: #666; margin: 5px 0 0 0;\">SevenMusic 团队</p>" +
                "</div>" +
                "</div>" +
                "</div>";
        MailUtil.send(SmtpConfig(), email.getEmail(), "注册SevenMusic", content, true);
        
        // 使用LocalDateTime
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        int time = (int) (instant.toEpochMilli() /1000);

        //保存验证码
        int c = registerMapper.SaveEmailCode(new vEmail(null, email.getEmail(), code, time));
        if (c == 0) {
            return Result.error("发送失败");
        }

        return Result.success("发送成功");
    }

    public Result<String> RegAccount(RegUser ru) {
        vEmail e = registerMapper.GetEmailCode(ru.getCode(), ru.getEmail());
        if (e == null) {
            return Result.error("验证码不存在或已过期");
        }
        if (!Objects.equals(e.getCode(), ru.getCode())) {
            return Result.error("验证码错误");
        }
        List<User> list = registerMapper.GetAllUser();
        for (User vEmail : list) {
            if (vEmail.getUsername().equals(ru.getEmail())) {
                return Result.error("邮箱已注册,请直接登入");
            }
        }
        
        // 验证时效性（5分钟）
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        int nowTime = (int) (instant.toEpochMilli() /1000);
        long codeTime = e.getCreatedAt();
        System.out.println("codeTime: " + codeTime);
        System.out.println(e);
        if (codeTime == 0){
            return Result.error("验证码为空");
        }
        if(nowTime - codeTime > 300000){
            return Result.error("验证码已过期，请重新获取");
        }


        int c = userMapper.Register(ru.getEmail(), ru.getPassword());
        if (c == 1) {
            String content = "<div style=\"max-width: 600px; margin: 0 auto; padding: 20px; font-family: Arial, sans-serif; background-color: #f8f9fa; border-radius: 10px;\">" +
                    "<div style=\"text-align: center; padding: 20px; background-color: #4CAF50; border-radius: 8px 8px 0 0;\">" +
                    "<h1 style=\"color: white; margin: 0; font-size: 24px;\">🎉 注册成功 🎉</h1>" +
                    "</div>" +
                    "<div style=\"padding: 30px; background-color: white; border-radius: 0 0 8px 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);\">" +
                    "<p style=\"font-size: 16px; color: #333; line-height: 1.6;\">亲爱的用户：</p>" +
                    "<p style=\"font-size: 16px; color: #333; line-height: 1.6;\">恭喜您成功注册 SevenMusic！</p>" +
                    "<div style=\"background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;\">" +
                    "<p style=\"font-size: 16px; color: #333; margin: 0;\">您的账号信息：</p>" +
                    "<p style=\"font-size: 14px; color: #666; margin: 10px 0 0 0;\">邮箱：" + ru.getEmail() + "</p>" +
                    "</div>" +
                    "<p style=\"font-size: 14px; color: #666; line-height: 1.6;\">现在您可以：</p>" +
                    "<ul style=\"font-size: 14px; color: #666; line-height: 1.6; padding-left: 20px;\">" +
                    "<li>登录您的账号</li>" +
                    "<li>完善个人资料</li>" +
                    "<li>开始探索音乐世界</li>" +
                    "</ul>" +
                    "<div style=\"margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee;\">" +
                    "<p style=\"font-size: 14px; color: #666; margin: 0;\">祝您使用愉快！</p>" +
                    "<p style=\"font-size: 14px; color: #666; margin: 5px 0 0 0;\">SevenMusic 团队</p>" +
                    "</div>" +
                    "</div>" +
                    "</div>";
            MailUtil.send(SmtpConfig(), ru.getEmail(), "注册成功 - SevenMusic", content, true);
        }
        return Result.success("注册成功");
    }

    private MailAccount SmtpConfig() {
        MailAccount account = new MailAccount();

        return account;
    }
}
