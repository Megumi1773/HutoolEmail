package cc.fun.hutoolemail.demos.web.Mapper;

import cc.fun.hutoolemail.demos.web.DTO.LoginUser;
import cc.fun.hutoolemail.demos.web.Entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("Select count(*)from user where username=#{username} and password=#{password}")
    int Login(LoginUser u);


    //    注册账号
    @Insert("insert into user(username,password) values(#{username},#{password})")
    int Register(@Param("username") String username, @Param("password") String password);
}
