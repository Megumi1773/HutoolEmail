package cc.fun.hutoolemail.demos.web.Controller;

import cc.fun.hutoolemail.demos.web.DTO.GetCodeEmail;
import cc.fun.hutoolemail.demos.web.DTO.RegUser;
import cc.fun.hutoolemail.demos.web.Misc.Result;
import cc.fun.hutoolemail.demos.web.Service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@CrossOrigin(origins = "*")
public class register {
    private final registerService registerService;

    @Autowired
    public register(registerService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/getcode")
    public Result<String> SendMail(@RequestBody GetCodeEmail email) {
        return registerService.sendMail(email);
    }
    @PostMapping("/reg")
    public Result<String> RegAccount(@RequestBody RegUser ru) {
        return registerService.RegAccount(ru);
    }
}
