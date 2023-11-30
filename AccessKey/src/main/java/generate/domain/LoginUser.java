package generate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
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
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

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
    private Integer isDelete;

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

    /**
     * 上一次登录归属地
     */
    private String ipAddress;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}