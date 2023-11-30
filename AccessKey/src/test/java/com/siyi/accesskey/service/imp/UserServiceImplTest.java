package com.siyi.accesskey.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siyi.accesskey.mapper.LoginUserMapper;

import com.siyi.accesskey.model.domain.LoginUser;
import com.siyi.accesskey.service.UserService;
import com.siyi.accesskey.utils.MD5Util;
import com.siyi.accesskey.utils.UUIDUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-10  22:45
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    LoginUserMapper loginUserMapper;
    @Autowired
    UserService userService;

    @Test
    void login() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("admin");
        loginUser.setPassword("@admin123");
    }

    @Test
    void register() {
        // 创建一个包含所有字段值的用户对象
        LoginUser fullUser = new LoginUser();
        String uuid = UUIDUtil.uuid32();
        fullUser.setUserId(uuid);
//        fullUser.setUsername("adminemail");
//        fullUser.setPassword("@admin123");
        //用户名的base64
//        String encodePassword = MD5Util.getEncodePassword(fullUser);
//        fullUser.setPassword(encodePassword);
        fullUser.setEmail("admin@example.com");
//        fullUser.setPhone("123456789");

        // 插入数据库
//        int result = loginUserMapper.insert(fullUser);

        boolean re = userService.save(fullUser);

        // 验证插入是否成功
//        assertEquals(1, result);
        assertTrue(re);


    }

    @Test
    void checkUsername() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("admin2");
//        QueryWrapper<LoginUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", loginUser.getUsername());
//        LoginUser result = loginUserMapper.selectOne(queryWrapper);
        LoginUser result = loginUserMapper.selectOne(new QueryWrapper<>(loginUser).eq("username", loginUser.getUsername()));
        if (result != null) {
            System.out.println("用户名已存在");
        } else {
            System.out.println("用户名不存在");
        }
        System.out.println(result);
    }
}