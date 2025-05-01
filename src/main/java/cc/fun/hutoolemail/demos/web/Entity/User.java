package cc.fun.hutoolemail.demos.web.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updated_at;
}
