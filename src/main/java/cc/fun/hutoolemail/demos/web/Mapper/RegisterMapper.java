package cc.fun.hutoolemail.demos.web.Mapper;

import cc.fun.hutoolemail.demos.web.DTO.RegUser;
import cc.fun.hutoolemail.demos.web.Entity.vEmail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegisterMapper {
    // 保存邮箱验证码
    @Insert("insert into vemail(email,code,created_at) values(#{email},#{code},#{created_at})")
    int SaveEmailCode(vEmail e);

    @Select("select  id, email, code, created_at from vemail where email = #{email}")
    vEmail GetEmailCode(RegUser  ru);
}
