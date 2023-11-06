package com.demo.shop_demo.user_operation.service.impl;

import com.demo.shop_demo.core.model.UserEntity;
import com.demo.shop_demo.user_operation.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;
    @Test
    public void save() {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("test");
            userEntity.setPassword("123456");
            userEntity.setEmail("");
            userEntity.setPhone("1234567890");
            userEntity.setName("test");
            userEntity.setUsertype(1);
            userEntity.setState(1);
            System.out.println(userEntity);

            userService.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}