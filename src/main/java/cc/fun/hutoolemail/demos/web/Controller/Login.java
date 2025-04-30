package cc.fun.hutoolemail.demos.web.Controller;

import cc.fun.hutoolemail.demos.web.DTO.LoginUser;
import cc.fun.hutoolemail.demos.web.Entity.User;
import cc.fun.hutoolemail.demos.web.Misc.Result;
import cc.fun.hutoolemail.demos.web.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@CrossOrigin(origins = "*")
public class Login {
    private final UserService userService;

    @Autowired
    public Login(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<Object> login(@RequestBody LoginUser u) {
        return userService.login(u);
    }
}
