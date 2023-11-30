package com.siyi.accesskey.model.domain;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**

 * @author 26481
 * @CreateTime: 2023-11-25  21:33
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class Password {

    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z]{6,18}$", message = "密码格式不正确")
    private String password;

    @NotEmpty(message = "密码不能为空")
    private String confirmPassword;

    @AssertTrue(message = "两次密码不一致")
    private boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}
