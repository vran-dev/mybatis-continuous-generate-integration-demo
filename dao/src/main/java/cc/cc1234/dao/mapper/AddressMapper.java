package cc.cc1234.dao.mapper;

import cc.cc1234.dao.model.Address;
import cc.cc1234.dao.model.AddressExample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper extends OneMapper<Address, AddressExample> {
}