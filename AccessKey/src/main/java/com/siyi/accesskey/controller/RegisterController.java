package com.siyi.accesskey.controller;

import com.siyi.accesskey.constant.ErrorCode;
import com.siyi.accesskey.ex.AuthenticationException;
import com.siyi.accesskey.model.domain.LoginUser;
import com.siyi.accesskey.model.domain.LoginUserCodeVO;
import com.siyi.accesskey.model.valid.*;
import com.siyi.accesskey.service.SmsService;
import com.siyi.accesskey.service.UserService;
import com.siyi.accesskey.utils.EmailSenderUtil;
import com.siyi.accesskey.utils.IpAddr;
import com.siyi.accesskey.utils.IpUtil;
import com.siyi.accesskey.utils.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.siyi.accesskey.constant.Constant.*;

/**
 * @author 26481
 * @CreateTime: 2023-11-09  22:35
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailSenderUtil emailSenderUtil;
    @Autowired
    private IpUtil ipUtil;

    @GetMapping()
    public ModelAndView registerPage() {
        return new ModelAndView("register/register");
    }

    /**
     * 检查用户名是否存在
     *
     * @param loginUser 只验证用户名
     * @return 存在返回true，不存在返回false
     */
    @PostMapping("/checkUsername")
    public Resp checkUsername(@Validated(UsernameGroup.class) @RequestBody LoginUser loginUser
            , BindingResult bindingResult
            , HttpServletRequest request
    ) throws AuthenticationException {
        // 判断用户名是否为空并校验
      if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        //校验通过,查找用户名
        userService.checkUsername(loginUser);
        return Resp.ok();

    }

    @PostMapping("/username")
    public Resp register(@Validated(UserRegisterGroup.class) @RequestBody LoginUserCodeVO loginUserCodeVO
            , BindingResult bindingResult
            , HttpServletRequest request
    ) throws AuthenticationException {
        // 判断用户名和密码是否为空并校验
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }

        //检查验证码,获取key sessionId 从redis中获取验证码
        String sessionId = request.getSession().getId();
        String captcha = redisTemplate.opsForValue().get(sessionId);
        //判断验证码是否正确
        if (!loginUserCodeVO.getCode().equalsIgnoreCase(captcha)) {
            throw new AuthenticationException(ErrorCode.CAPTCHA_ERROR);
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(loginUserCodeVO.getUsername());
        loginUser.setPassword(loginUserCodeVO.getPassword());
        // 校验用户名是否已经存在重复
        userService.checkUsername(loginUser);

        //获取ip地址
        String city = ipAddr(request);
        loginUser.setIpAddress(city);
        //校验通过,进行注册
        boolean result = userService.registerByUsername(loginUser);
        if (!result) {
            throw new AuthenticationException(ErrorCode.DATABASE_ERROR);
        }
        return Resp.ok("用户注册成功!");
    }

    /**
     * 手机注册并校验验证码
     *
     * @param loginUserCodeVO 验证手机号,用户名,密码
     * @param bindingResult   错误信息
     * @return Resp 成功返回操作结果
     * @validated(PhoneRegisterGroup.class) @RequestBody LoginUserCodeVO loginUserCodeVO
     */
    @PostMapping("/phone")
    public Resp registerPhone(@Validated(PhoneRegisterGroup.class) @RequestBody LoginUserCodeVO loginUserCodeVO
            , BindingResult bindingResult
            , HttpServletRequest request
    ) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setPhone(loginUserCodeVO.getPhone());
        loginUser.setPassword(loginUserCodeVO.getPassword());
        // 校验验证码
        String phone = loginUserCodeVO.getPhone();
        String code = loginUserCodeVO.getCode();
        String redisCode = redisTemplate.opsForValue().get(phone);

        if (!code.equals(redisCode)) {
            throw new AuthenticationException(ErrorCode.CAPTCHA_ERROR);
        }
        // 校验手机号是否已经存在重复
        if (userService.phoneExist(loginUser) != null) {
            throw new AuthenticationException(ErrorCode.PHONE_EXIST);
        }
        //获取ip地址
        String city = ipAddr(request);
        loginUser.setIpAddress(city);
        //校验通过,进行注册
        boolean register = userService.registerByPhone(loginUser);
        if (!register) {
            //注册失败
            throw new AuthenticationException(ErrorCode.DATABASE_ERROR);
        }
        //注册成功,删除redis中的验证码
        redisTemplate.delete(phone);
        return Resp.ok("注册成功!");
    }


    /**
     * 点击邮箱链接激活账号后的页面 /registerByUsername/email?sib=激活码
     *
     * @param activeCode 激活码
     * @return ModelAndView
     */
    @GetMapping("/email")
    public ModelAndView registerEmailPage(@RequestParam("sib") String activeCode) {
        ModelAndView mv = new ModelAndView("register/EmailResult");
        //判断激活码是否存在,防止自动拆箱空指针异常
        if (Boolean.FALSE.equals(redisTemplate.hasKey(activeCode))) {
            mv.addObject(MESSAGE, "激活码不存在!");
            return mv;
        }
        //激活码存在,对value+1
        redisTemplate.opsForValue().increment(activeCode);
        mv.addObject(MESSAGE, "激活成功!,请在五分钟中之内完成注册");
        return mv;
    }

    /**
     * 邮箱注册表单提交
     *
     * @param loginUser     只验证邮箱格式
     * @param bindingResult 错误信息
     * @return Resp 成功返回操作结果
     */
    @PostMapping("/email")
    public Resp registerEmail(@Validated(EmailRegisterGroup.class) @RequestBody LoginUser loginUser
            , BindingResult bindingResult
            , HttpServletRequest request
    ) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        // 校验邮箱是否已经存在重复
        if (userService.emailExist(loginUser) != null) {
            throw new AuthenticationException(ErrorCode.EMAIL_EXIST);
        }
        // 校验邮箱是否是同一个邮箱,加密后的邮箱作为key
        String activeCode = emailSenderUtil.generateSib(loginUser.getEmail());
        //判断value是否等于1 为1则认为已经激活
        String value = redisTemplate.opsForValue().get(activeCode);
        boolean result = "1".equals(value);
        if (!result) {
            throw new AuthenticationException(ErrorCode.EMAIL_NOT_ACTIVATED);
        }
        //获取ip地址
        String city = ipAddr(request);
        loginUser.setIpAddress(city);
        //校验通过,进行注册
        boolean register = userService.registerByEmail(loginUser);
        if (!register) {
            //注册失败
            throw new AuthenticationException(ErrorCode.DATABASE_ERROR);
        }
        //注册成功,删除redis中的激活码
        redisTemplate.delete(activeCode);
        return Resp.ok("注册成功!");
    }

    /**
     * 获取ip所在城市
     *
     * @param request 请求
     * @return 返回城市
     */
    private String ipAddr(HttpServletRequest request) {
        String ipAddress =ipUtil.getIpAddr(request);
        Map<String, Object> ipRegion = IpAddr.getIpRegion(ipAddress);
        return (String) ipRegion.get("城市");
    }
}
