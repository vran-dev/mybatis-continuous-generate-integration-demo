package dao.mapper;

import cc.cc1234.api.Application;
import cc.cc1234.dao.enums.Gender;
import cc.cc1234.dao.mapper.UserMapper;
import cc.cc1234.dao.model.User;
import cc.cc1234.dao.model.UserExample;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@Transactional
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsertSelective() {
        User user = User.builder()
                .gender(Gender.MALE)
                .username(UUID.randomUUID().toString())
                .build();
        int i = userMapper.insertSelective(user);

        Assertions.assertEquals(1, i);

        User entity = userMapper.selectByUsername(user.getUsername());
        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getCreateAt());
        Assertions.assertNotNull(entity.getUpdateAt());
        Assertions.assertEquals(user.getGender(), entity.getGender());
    }


    @Test
    public void testSelectOneByExample() {
        User user = User.builder()
                .gender(Gender.MALE)
                .username(UUID.randomUUID().toString())
                .build();
        userMapper.insertSelective(user);
        Assertions.assertNotNull(user.getId());

        UserExample example = new UserExample();
        example.createCriteria()
                .andUsernameEqualTo(user.getUsername());
        Optional<User> userOption = userMapper.selectOneByExample(example);
        Assertions.assertTrue(userOption.isPresent());
    }
}
