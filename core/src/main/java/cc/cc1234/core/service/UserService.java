package cc.cc1234.core.service;

import cc.cc1234.dao.enums.Gender;
import cc.cc1234.dao.mapper.AddressMapper;
import cc.cc1234.dao.mapper.UserMapper;
import cc.cc1234.dao.model.Address;
import cc.cc1234.dao.model.AddressExample;
import cc.cc1234.dao.model.User;
import cc.cc1234.dao.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AddressMapper addressMapper;

    public List<User> listUsers(int page, int size) {
        int start = Math.max(1, page);
        int offset = (start - 1) * size;
        return userMapper.selectByPage(offset, size);
    }

    public Long countUser() {
        return userMapper.countUser();
    }

    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public User saveUser(String username, Gender gender) {
        User entity = new User();
        entity.setUsername(username);
        entity.setGender(gender);
        userMapper.insertSelective(entity);

        AddressExample example = new AddressExample();
        example.createCriteria()
                .andCityIsNotNull();
        example.setOrderByClause("create_at desc");
        List<Address> addresses = addressMapper.selectByExample(example);
        return userMapper.selectByPrimaryKey(entity.getId());
    }

    public void saveUser(Long id, String username) {
        User row = User.builder()
                .username(username)
                .build();
        UserExample example = UserExample.create()
                .createCriteria()
                .andIdEqualTo(id).example();
        userMapper.updateByExampleSelective(row, example);
    }
}
