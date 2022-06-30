package cc.cc1234.dao.mapper;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T> {

    T selectByPrimaryKey(Serializable identity);

    int deleteByPrimaryKey(Serializable identity);

    int insertSelective(T record);

    int insert(T record);

    int updateByPrimaryKey(T record);

    int updateByPrimaryKeySelective(T record);

    List<T> selectAll();
}
