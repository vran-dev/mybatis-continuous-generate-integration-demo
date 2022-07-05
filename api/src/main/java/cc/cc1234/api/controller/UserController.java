package cc.cc1234.api.controller;

import cc.cc1234.api.vo.UserSaveRequest;
import cc.cc1234.core.service.UserService;
import cc.cc1234.dao.enums.Gender;
import cc.cc1234.dao.mapper.UserMapper;
import cc.cc1234.dao.model.User;
import cc.cc1234.dao.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/users")
    public List<User> getUsername(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "20") Integer size) {
        return userService.listUsers(page, size);
    }

    @GetMapping("/users/query")
    public List<User> getUsername(@RequestParam Map<String, String> query) {
        UserExample example = UserExample.create()
                .createCriteria()
                .andUsernameLike(query.get("username"))
                .andGenderEqualTo(Gender.valueOf(query.get("gender")))
                .example();
        return userMapper.selectByExample(example);
    }

    @GetMapping("/users/statistics/total")
    public Long countUser() {
        return userService.countUser();
    }

    @GetMapping("/users/{username}")
    public User findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody @Valid UserSaveRequest request) {
        return userService.saveUser(request.getUsername(), request.getGender());
    }

    @PatchMapping("/users")
    public void updateUsr(@RequestBody UserSaveRequest request) {
        userService.saveUser(request.getId(), request.getUsername());
    }
}
