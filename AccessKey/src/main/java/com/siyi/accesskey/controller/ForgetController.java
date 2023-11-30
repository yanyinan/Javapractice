package com.siyi.accesskey.controller;

import com.siyi.accesskey.constant.ErrorCode;
import com.siyi.accesskey.ex.AuthenticationException;
import com.siyi.accesskey.model.domain.ForgetCode;
import com.siyi.accesskey.model.domain.LoginUser;
import com.siyi.accesskey.model.domain.Password;
import com.siyi.accesskey.service.UserService;
import com.siyi.accesskey.utils.AesUtil;
import com.siyi.accesskey.utils.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

import static com.siyi.accesskey.constant.Constant.*;

/**
 * @author 26481
 * @CreateTime: 2023-11-18  11:30
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@Slf4j
public class ForgetController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserService userService;

    @GetMapping("/forget")
    public ModelAndView forgetPage() {
        return new ModelAndView("forget/forget");
    }

    @PostMapping("/forget")
    public Resp forget(@Validated @RequestBody ForgetCode forgetCode
            , BindingResult bindingResult
            , HttpServletRequest request
    ) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        //检查短信或者邮箱验证码是否正确
        String code = redisTemplate.opsForValue().get(forgetCode.getKey());
        if (!forgetCode.getCode().equals(code)) {
            throw new AuthenticationException(ErrorCode.CAPTCHA_ERROR);
        }
        LoginUser loginUser = new LoginUser();
        LoginUser result = null;
//        判断是邮箱还是手机号
        if (forgetCode.getKey().contains("@")) {
            //邮箱
            loginUser.setEmail(forgetCode.getKey());
            result = userService.emailExist(loginUser);
        } else {
            //手机号
            loginUser.setPhone(forgetCode.getKey());
            result = userService.phoneExist(loginUser);
        }
        if (result == null) {
            throw new AuthenticationException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        //将用户信息存入session
        request.getSession().setAttribute(USER_MODIFY_STATE, result);
        String way = result.getPhone() == null ? FORGET_VALUE_EMAIL : FORGET_VALUE_PHONE;
        //校验完毕,将用户token存入redis
        String sessionId = request.getSession().getId();
        //将用户session id 存入redis,有效时间3分钟
        redisTemplate.opsForValue().set(FORGET_KEY + sessionId, way, 3, TimeUnit.MINUTES);
        return Resp.ok("'认证成功!请在3分钟之内修改密码'");

    }

    @PostMapping("/modify")
    public Resp modify(@Validated @RequestBody Password password
            , BindingResult bindingResult
            , HttpServletRequest request
    ) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        //获取用户session id
        String sessionId = request.getSession().getId();
        String way = redisTemplate.opsForValue().get(FORGET_KEY + sessionId);
        if (way == null) {
            throw new AuthenticationException(ErrorCode.ACCESS_DENIED);
        }
        //获取用户信息
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(USER_MODIFY_STATE);
        LoginUser result = userService.modify(loginUser);
        if (result == null) {
            Resp.error("修改失败");
        }
        //删除redis中的session id
        redisTemplate.delete(FORGET_KEY + sessionId);
        //删除session中的用户信息
        request.getSession().removeAttribute(USER_MODIFY_STATE);
        //修改密码
        request.getSession().removeAttribute(USER_MODIFY_STATE);
        return Resp.ok("修改成功");
    }
}
