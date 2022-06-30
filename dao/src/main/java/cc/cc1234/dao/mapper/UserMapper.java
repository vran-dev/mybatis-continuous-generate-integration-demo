package cc.cc1234.dao.mapper;

import cc.cc1234.dao.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    User selectByUsername(String username);

    long countUser();

    @Select("select * from user limit #{offset}, #{size}")
    @ResultMap("BaseResultMap")
    List<User> selectByPage(@Param("offset") Integer offset,
                            @Param("size") Integer size);

}
