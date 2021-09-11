package cc.cc1234.api.controller;

import cc.cc1234.api.vo.UserSaveRequest;
import cc.cc1234.core.service.UserService;
import cc.cc1234.dao.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserEntity> getUsername(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "20") Integer size) {
        return userService.listUsers(page, size);
    }

    @GetMapping("/users/statistics/total")
    public Long countUser() {
        return userService.countUser();
    }

    @GetMapping("/users/{username}")
    public UserEntity findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/users")
    public UserEntity saveUser(@RequestBody @Valid UserSaveRequest request) {
        return userService.saveUser(request.getUsername(), request.getGender());
    }
}
