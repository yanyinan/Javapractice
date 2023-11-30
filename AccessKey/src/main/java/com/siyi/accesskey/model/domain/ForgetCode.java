package com.siyi.accesskey.model.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-25  20:26
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class ForgetCode {
    @Pattern(regexp = "^(1[3456789]\\d{9}|[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+)$", message = "手机或者邮箱格式不正确")
    @NotEmpty(message = "手机号或者邮箱不能为空")
    private String key;
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
