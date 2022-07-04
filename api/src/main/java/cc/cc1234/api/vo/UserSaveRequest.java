package cc.cc1234.api.vo;

import cc.cc1234.dao.enums.Gender;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserSaveRequest {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private Gender gender;
}
