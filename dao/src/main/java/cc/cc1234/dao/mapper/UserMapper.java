package cc.cc1234.dao.mapper;

import cc.cc1234.dao.model.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends UserEntityMapper {

    UserEntity selectByUsername(String username);

    long countUser();

    @Select("select * from user limit #{offset}, #{size}")
    List<UserEntity> selectByPage(@Param("offset") Integer offset,
                                  @Param("size") Integer size);

    @Select(value = "select * from user")
    List<UserEntity> selectAll();

}
