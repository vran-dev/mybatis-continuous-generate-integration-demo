package cc.cc1234.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OneMapper<T, E>  {
    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(Long id);

    int insert(T row);

    int insertSelective(T row);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(Long id);

    int updateByExampleSelective(T row, E example);

    int updateByExample(T row, E example);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKey(T row);
}