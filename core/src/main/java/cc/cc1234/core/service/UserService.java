package cc.cc1234.core.service;

import cc.cc1234.dao.enums.Gender;
import cc.cc1234.dao.mapper.UserMapper;
import cc.cc1234.dao.model.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<UserEntity> listUsers(int page, int size) {
        int start = Math.max(1, page);
        int offset = (start - 1) * size;
        return userMapper.selectByPage(offset, size);
    }

    public Long countUser() {
        return userMapper.countUser();
    }

    public UserEntity findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public UserEntity saveUser(String username, Gender gender) {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setGender(gender);
        userMapper.insertSelective(entity);
        return userMapper.selectByPrimaryKey(entity.getId())
                .orElseThrow(IllegalStateException::new);
    }
}
