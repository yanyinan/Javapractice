package com.siyi.accesskey.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.siyi.accesskey.model.valid.EmailGroup;
import com.siyi.accesskey.model.valid.PasswordGroup;
import com.siyi.accesskey.model.valid.PhoneGroup;
import com.siyi.accesskey.model.valid.UsernameGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 
 * @author 26481
 * @TableName login_user
 */
@TableName(value ="login_user")
@Data
public class LoginUser implements Serializable {
    /**
     * 用户id
     */
    @TableId
    private String userId;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空", groups = {UsernameGroup.class})
    @Pattern(regexp = "^[a-zA-Z]{4,16}$", message = "用户名必须为4到16位的大小写字母", groups = {UsernameGroup.class})
    private String username;

    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空", groups = {PasswordGroup.class})
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z]{6,18}$", message = "密码必须6到16位，包括大小写字母且不能有空格特殊字符", groups = {PasswordGroup.class})
    private String password;

    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空", groups = {EmailGroup.class})
    @Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$", message = "邮箱格式不正确", groups = {EmailGroup.class})
    private String email;

    /**
     * 电话
     */
    @NotEmpty(message = "手机号不能为空", groups = {PhoneGroup.class})
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确", groups = {PhoneGroup.class})
    private String phone;

    /**
     * 账户状态(1代表正常,0代表异常)
     */
    private Integer userStatus;

    /**
     * 用户角色(0代表管理员,1代表用户)
     */
    private Integer userRole;

    /**
     * 是否删除(0未删除1已删除)
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 上一次登录归属地
     */
    private String ipAddress;
    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date loginTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}