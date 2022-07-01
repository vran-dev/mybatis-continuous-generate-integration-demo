package cc.cc1234.dao.mapper;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface OneMapper<T, E>  {
    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(Long id);

    int insert(T row);

    int insertSelective(T row);

    List<T> selectByExample(E example);

    Optional<T> selectOneByExample(E example);

    T selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") T row, @Param("example") E example);

    int updateByExample(@Param("row") T row, @Param("example") E example);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKey(T row);
}