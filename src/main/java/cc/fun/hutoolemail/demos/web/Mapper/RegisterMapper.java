package cc.fun.hutoolemail.demos.web.Mapper;

import cc.fun.hutoolemail.demos.web.DTO.RegUser;
import cc.fun.hutoolemail.demos.web.Entity.User;
import cc.fun.hutoolemail.demos.web.Entity.vEmail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RegisterMapper {
    // 保存邮箱验证码
    @Insert("insert into vemail(email,code,created_at) values(#{email},#{code},#{createdAt})")
    int SaveEmailCode(vEmail e);

    @Select("select * from vemail where code = #{code} and email = #{email} order by created_at desc limit 1")
    vEmail GetEmailCode(@Param("code") Integer code, @Param("email") String email);

    @Select("select id, username, password, created_at, updated_at from user")
    List<User> GetAllUser();
}
